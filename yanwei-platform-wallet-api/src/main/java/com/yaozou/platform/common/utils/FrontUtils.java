package com.yanwei.platform.common.utils;


import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.propertyeditors.LocaleEditor;
import org.springframework.context.MessageSource;
import com.yanwei.platform.common.utils.URLHelper.PageInfo;

/**
 * 前台工具类
 * @author luojianhong
 * @version $Id: FrontUtils.java, v 0.1 2017年10月13日 上午10:46:05 luojianhong Exp $
 */
public class FrontUtils {
    /**
     * 页面没有找到
     */
    public static final String PAGE_NOT_FOUND   = "pageNotFound";
    /**
     * 操作成功页面
     */
    public static final String SUCCESS_PAGE     = "successPage";
    /**
     * 操作失败页面
     */
    public static final String ERROR_PAGE       = "errorPage";
    /**
     * 信息提示页面
     */
    public static final String MESSAGE_PAGE     = "messagePage";
    /**
     * 系统资源路径
     */
    public static final String RES_SYS          = "resSys";
    /**
     * 模板资源路径
     */
    public static final String RES_TPL          = "res";
    /**
     * 模板资源表达式
     */
    public static final String RES_EXP          = "${res}";
    /**
     * 手机模板资源路径
     */
    public static final String MOBILE_RES_TPL   = "mobileRes";
    /**
     * 部署路径
     */
    public static final String BASE             = "base";
    /**
     * 站点
     */
    public static final String SITE             = "site";
    /**
     * 用户
     */
    public static final String USER             = "user";
    /**
     * 页码
     */
    public static final String PAGE_NO          = "pageNo";
    /**
     * 总条数
     */
    public static final String COUNT            = "count";
    /**
     * 起始条数
     */
    public static final String FIRST            = "first";

    /**
     * 页面完整地址
     */
    public static final String LOCATION         = "location";
    /**
     * 页面翻页地址
     */
    public static final String HREF             = "href";
    /**
     * href前半部（相对于分页）
     */
    public static final String HREF_FORMER      = "hrefFormer";
    /**
     * href后半部（相对于分页）
     */
    public static final String HREF_LATTER      = "hrefLatter";

    /**
     * 传入参数，列表样式。
     */
    public static final String PARAM_STYLE_LIST = "styleList";
    /**
     * 传入参数，系统预定义翻页。
     */
    public static final String PARAM_SYS_PAGE   = "sysPage";
    /**
     * 传入参数，用户自定义翻页。
     */
    public static final String PARAM_USER_PAGE  = "userPage";

    /**
     * 返回页面
     */
    public static final String RETURN_URL       = "returnUrl";

    /**
     * 国际化参数
     */
    public static final String ARGS             = "args";

    /**
     * 获得模板路径。将对模板文件名称进行本地化处理。
     * 
     * @param request
     * @param solution
     *            方案路径
     * @param dir
     *            模板目录。
     * @param name
     *            模板名称。
     * @return
     */
    public static String getTplPath(HttpServletRequest request, String solution, String dir,
                                    String name) {
        String equipment = (String) request.getAttribute("ua");
        if (StringUtils.isNotBlank(equipment) && equipment.equals("mobile")) {
            //solution=site.getMobileSolutionPath();
        }
        return solution + "/" + dir + "/" + name;
    }

    /**
     * 获得模板路径。将对模板文件名称进行本地化处理。
     * 
     * @param messageSource
     * @param lang
     *            本地化语言
     * @param solution
     *            方案路径
     * @param dir
     *            模板目录。不本地化处理。
     * @param name
     *            模板名称。本地化处理。
     * @return
     */
    public static String getTplPath(MessageSource messageSource, String lang, String solution,
                                    String dir, String name) {
        LocaleEditor localeEditor = new LocaleEditor();
        localeEditor.setAsText(lang);
        return solution + "/" + dir + "/" + name;
    }

    /**
     * 获得模板路径。不对模板文件进行本地化处理。
     * 
     * @param solution
     *            方案路径
     * @param dir
     *            模板目录。不本地化处理。
     * @param name
     *            模板名称。不本地化处理。
     * @return
     */
    public static String getTplPath(String solution, String dir, String name) {
        return solution + "/" + dir + "/" + name;
    }

    /**
     * 页面没有找到
     * 
     * @param request
     * @param response
     * @return 返回“页面没有找到”的模板
     */
    public static String pageNotFound(HttpServletRequest request, HttpServletResponse response,
                                      Map<String, Object> model) {
        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        frontData(request, model);
        return getTplPath(request, "", "", PAGE_NOT_FOUND);
    }

    /**
     * 成功提示页面
     * 
     * @param request
     * @param model
     * @return
     */
    public static String showSuccess(HttpServletRequest request, Map<String, Object> model,
                                     String nextUrl) {

        //frontData(request, model, site);
        if (!StringUtils.isBlank(nextUrl)) {
            model.put("nextUrl", nextUrl);
        }

        return "";
    }

    /**
     * 错误提示页面
     * 
     * @param request
     * @param response
     * @param model
     * @return
     */
    public static String showError(HttpServletRequest request, HttpServletResponse response,
                                   Map<String, Object> model) {
        //r
        return "";
    }

    /**
     * 信息提示页面
     * 
     * @param request
     * @param model
     * @param message
     *            进行国际化处理
     * @return
     */
    public static String showMessage(HttpServletRequest request, Map<String, Object> model,
                                     String message, String... args) {

        /*frontData(request, model);
        model.put(MESSAGE, message);
        if (args != null) {
        	model.put(ARGS, args);
        }*/
        //return getTplPath(request,MESSAGE_PAGE);

        return "";
    }

    /**
     * 显示登录页面
     * 
     * @param request
     * @param model
     * @param site
     * @param message
     * @return
     */
    public static String showLogin(HttpServletRequest request, Map<String, Object> model,
                                   String message) {

        StringBuilder buff = new StringBuilder("redirect:");
        /*	buff.append(site.getLoginUrl()).append("?");
        	buff.append(RETURN_URL).append("=");
        	buff.append(RequestUtils.getLocation(request));
        	if (!StringUtils.isBlank(site.getProcessUrl())) {
        		buff.append("&").append(PROCESS_URL).append(site.getProcessUrl());
        	}*/
        return buff.toString();
    }

    /**
     * 显示登录页面
     * 
     * @param request
     * @param model
     * @param site
     * @return
     */
    public static String showLogin(HttpServletRequest request, Map<String, Object> model) {
        return showLogin(request, model, "true");
    }

    /**
     * 为前台模板设置公用数据
     * 
     * @param request
     * @param model
     */
    public static void frontData(HttpServletRequest request, Map<String, Object> map) {
        String location = RequestUtils.getLocation(request);
        frontData(map, location);
    }

    public static void frontData(Map<String, Object> map, String location) {
        map.put(LOCATION, location);
    }

    public static void putLocation(Map<String, Object> map, String location) {
        map.put(LOCATION, location);
    }

    /**
     * 为前台模板设置分页相关数据
     * 
     * @param request
     * @param map
     */
    public static void frontPageData(HttpServletRequest request, Map<String, Object> map) {
        int pageNo = URLHelper.getPageNo(request);
        PageInfo info = URLHelper.getPageInfo(request);
        String href = info.getHref();
        String hrefFormer = info.getHrefFormer();
        String hrefLatter = info.getHrefLatter();
        frontPageData(pageNo, href, hrefFormer, hrefLatter, map);
    }

    /**
     * 为前台模板设置分页相关数据
     * 
     * @param pageNo
     * @param href
     * @param urlFormer
     * @param urlLatter
     * @param map
     */
    public static void frontPageData(int pageNo, String href, String hrefFormer, String hrefLatter,
                                     Map<String, Object> map) {
        map.put(PAGE_NO, pageNo);
        map.put(HREF, href);
        map.put(HREF_FORMER, hrefFormer);
        map.put(HREF_LATTER, hrefLatter);
    }

}
