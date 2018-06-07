package com.yaozou.platform.common.interceptor;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.alibaba.druid.util.StringUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yaozou.platform.common.constant.Constants;
import com.yaozou.platform.common.domain.TokenModel;
import com.yaozou.platform.common.manager.TokenManager;
import com.yaozou.platform.common.utils.ApiPost;
import com.yaozou.platform.common.utils.RequestUtils;
import com.yaozou.platform.member.domain.ApiOut;

import lombok.extern.slf4j.Slf4j;

/**
 * 自定义拦截器，判断此次请求是否有权限
 * @author luojianhong
 * @version $Id: AuthorizationInterceptor.java, v 0.1 2017年11月15日 下午3:02:27 luojianhong Exp $
 */
@Component
@Slf4j
public class AuthorizationInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private TokenManager manager;

    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
    	String url = request.getRequestURL().toString();
    	//从header中得到token
        String token = request.getHeader(Constants.AUTHORIZATION);
    	//打印头部信息
        RequestUtils.getHeadersInfo(request);
    	
        log.info("token为："+token+"请求地址："+url);
        if (isInterceptionUrl(url)) {
            ApiPost.token = token;
            //获取token信息
            TokenModel model = manager.getToken(token);
            boolean haveToVerifyToken = haveToVerifyToken(url); //是否必须验证token
            if (haveToVerifyToken){
                //验证token
                if (model!=null&&manager.checkToken(token)) {
                    //权限控制
                    boolean flag = authContr(model,url);
                    if (flag) {
                        //如果token验证成功，将token对应的用户id存在request中，便于之后注入
                        request.setAttribute(Constants.CURRENT_USER, model);
                        return true;
                    }
                    return false;
                }else {
                    ApiOut<String> apiOut=ApiOut.failure(Constants.TOKEN_INVALID_CODE, Constants.TOKEN_INVALID_MESSAGE);
                    out(response,apiOut);
                    //如果验证token失败，并且方法注明了Authorization，返回401错误
                    return false;
                }
            }else{
                //如果token验证成功，将token对应的用户id存在request中，便于之后注入
                request.setAttribute(Constants.CURRENT_USER, model);
            }
		}
        return true;
    }
    
    /**
     * 权限控制
     * @param url
     * @return
     */
    private boolean authContr(TokenModel tokenModel, String url) {
    	String userType = tokenModel.getUserType();
    	//用户类型 Admin(1, "系统管理员"), Business(11, "商家端(管理员)"), Client(12, "移动端(APP端及作家)"), Taiping(13, "太平SDK");
    	boolean flag = false;
		String appPrix = "/app/";
		String cloudPrix = "/web/";
		String merchantPrix = "/web/"; 
		String innerPrix = "/platform/"; //内部
		if ("Client".equals(userType) || "Taiping".equals(userType)) { //APP端
			if(url.indexOf(appPrix) >= 0 ||url.indexOf(innerPrix) >= 0){
				flag = true;
			}
			
		}else if ("Admin".equals(userType) || "Business".equals(userType)) {
			String specUrl = "/web/user/sett/record/approve";//审核提现
			if (url.indexOf(specUrl) >= 0 && 
					StringUtils.isEmpty(tokenModel.getMerchantSid())) { //云平台
				flag = true;
			}else if (url.indexOf(cloudPrix) >= 0 || url.indexOf(merchantPrix) >= 0) {
				flag = true;
			}
		}
		return flag;
	}

	/**
     * 返回输出json
     *
     * @param response
     */
    private static final void out(HttpServletResponse response, ApiOut<String> apiOut) {
        ObjectMapper objectMapper = new ObjectMapper();
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter out = null;
        try {
            out = response.getWriter();
            out.append(objectMapper.writeValueAsString(apiOut));
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }
    
    public boolean isInterceptionUrl(String url) {
		String[] notInterUrl = {"/platform/user/accDetail/refundOrder"};
		/*for (String str : notInterUrl) {
			if (url.indexOf(str) > -1) {
				return false;
			}
		}*/
		return false;
	}

	public  boolean haveToVerifyToken(String url) {
        String[] notInterUrl = {"/app/user/acount/sendMsgForBindPhone","/app/user/acount/balance",
                                "/platform/user/acount/balance","/platform/user/acount/rebindPhone","/platform/user/acount/sendMsgForBindPhone"};
        /*for (String str : notInterUrl) {
            if (url.indexOf(str) > -1) {
                return false;
            }
        }*/
        return false;
    }
}