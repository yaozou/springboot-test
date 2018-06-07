package com.yaozou.platform.common.config;

import javax.annotation.PostConstruct;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.core.annotation.Order;
import org.springframework.core.convert.support.GenericConversionService;
 
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.support.ConfigurableWebBindingInitializer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import com.yaozou.platform.SpringBootMemebersApplication;
import com.yaozou.platform.common.interceptor.AuthorizationInterceptor;
import com.yaozou.platform.common.spring.DateConvertEditor;
import com.yaozou.platform.common.spring.StringConverterTrimmerEditor;


/**
 * 资源文件位置
 * @author luojianhong
 * @version $Id: WebConfigurer.java, v 0.1 2017年10月9日 下午4:40:04 luojianhong Exp $
 */
@Component
@ComponentScan(basePackageClasses = SpringBootMemebersApplication.class, includeFilters = @Filter(Controller.class), useDefaultFilters = false)
public class WebConfigurer extends WebMvcConfigurerAdapter {
    
   

    @Autowired
    RequestMappingHandlerAdapter     requestMappingHandlerAdapter;
    //上传路径
    @Autowired
    private BootMemebersConfig       bootMemebersConfig;
    @Autowired
    private AuthorizationInterceptor authorizationInterceptor;

    @PostConstruct
    public void initEditableValidation() {

        ConfigurableWebBindingInitializer initializer = (ConfigurableWebBindingInitializer) requestMappingHandlerAdapter
            .getWebBindingInitializer();
        if (initializer.getConversionService() != null) {

            GenericConversionService genericConversionService = (GenericConversionService) initializer
                .getConversionService();

            genericConversionService.addConverter(new DateConvertEditor());
            genericConversionService.addConverter(new StringConverterTrimmerEditor());

            //增加通用较检类
            //initializer.setValidator(validator);
        }
    }
        
 

    @Bean
    public AuthorizationInterceptor getSecurityInterceptor() {
        return new AuthorizationInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration addInterceptor = registry.addInterceptor(authorizationInterceptor);
        // 排除配置
        
      //  addInterceptor.excludePathPatterns("/swagger-ui.html**");
        // 拦截配置
        //addInterceptor.addPathPatterns("/**");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/files/**")
            .addResourceLocations("file:///" + bootMemebersConfig.getUploadPath());
    }

    @Bean
    @Order(1)
    public static DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator daap = new DefaultAdvisorAutoProxyCreator();
        daap.setProxyTargetClass(true);
        return daap;
    }

    @Bean
    public ServletRegistrationBean druidServlet() {
        ServletRegistrationBean reg = new ServletRegistrationBean();
        reg.setServlet(new StatViewServlet());
        reg.addUrlMappings("/druid/*");
        reg.addInitParameter("allow", ""); //白名单
        reg.addInitParameter("loginUsername", "admnYw2017");
        reg.addInitParameter("loginPassword", "Yw2017#@!");
        return reg;
    }

    @Bean
    public FilterRegistrationBean filterRegistrationBean() {

        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new WebStatFilter());
        filterRegistrationBean.addUrlPatterns("/*");
        filterRegistrationBean.addInitParameter("exclusions",
            "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");

        return filterRegistrationBean;
    }
    
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("*")
        .allowedMethods("GET", "POST", "DELETE", "PUT")
        .maxAge(3600)
        .allowedOrigins("*");
    }

}