package com.yanwei.platform.common.utils;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.druid.util.StringUtils;
import com.yanwei.platform.common.constant.Constants;
import com.yanwei.platform.common.domain.TokenModel;


/**
 * 
 * @author luojianhong
 * @version $Id: HttpContextUtils.java, v 0.1 2017年10月10日 上午11:14:46 luojianhong
 *          Exp $
 */
public class HttpContextUtils {

    public static final String SYS_USER_ID   = "-1";
    public static final String SYS_USER_NAME = "系统操作";

    public static final String USER_ID       = "0";
    public static final String USER_NAME     = "无效用户";
    public static final String USER_NAME_TOKEN     = "无效token";

    public static HttpServletRequest getHttpServletRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
            .getRequest();
    }

    /**
     * 获取用户信息
     * 
     * @return
     */
    public static TokenModel getTokenModel() {
        if (RequestContextHolder.getRequestAttributes() == null)
            return new TokenModel(SYS_USER_ID, SYS_USER_NAME,USER_NAME_TOKEN);
        TokenModel tokenModel = (TokenModel) HttpContextUtils.getHttpServletRequest()
            .getAttribute(Constants.CURRENT_USER);
        if (tokenModel == null)
            tokenModel = new TokenModel(USER_ID, USER_NAME,USER_NAME_TOKEN);
        return tokenModel;
    }
    
    public static String getUserSid() {
		return getTokenModel().getUserId();
	}

    /**
     * 获取用户id
     * @return
     */
    public static Long getUserId() {
    	String userSid = getUserSid();
    	if (!StringUtils.isEmpty(userSid)) {
			return Long.valueOf(NumberUtils.getConvertStrNum(userSid));
		}
        return null;
    }
    
    /**
     * 获取用户token
     * @return
     */
    public static String getToken() {
        return getTokenModel().getToken();
    }
    
    /**
     * 店铺平台
     * @return
     */
    public static String getShopId() {
        return getTokenModel().getShopSid();
    }
    
    /**
     * 商家平台
     * @return
     */
    public static String getMerchantSid() {
        return getTokenModel().getMerchantSid();
    }
    
    public static Long getMerchantId(){
    	String merchantSid = getTokenModel().getMerchantSid();
    	if (!StringUtils.isEmpty(merchantSid)) {
			return NumberUtils.getConvertStrNum(merchantSid);
		}
    	return null;
    }

    /**
     * 获得版本号
     * @return
     */
    public  static String getVersion(){
       return HttpContextUtils.getHttpServletRequest().getHeader("api-ver");
    }
}
