package com.yanwei.platform.common.config;

import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.web.servlet.ErrorPage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

 
/**
 *  错误页面配置
 * @author luojianhong
 * @version $Id: ErrorPageConfig.java, v 0.1 2017年7月10日 下午4:44:01 luojianhong Exp $
 */
@Configuration
public class ErrorPageConfig {

	@Bean
	public EmbeddedServletContainerCustomizer embeddedServletContainerCustomizer() {
		return new MyCustomizer();
	}

	private static class MyCustomizer implements EmbeddedServletContainerCustomizer {

		/**
		 * 自定义错误文件位置
		 */
		@Override
		public void customize(ConfigurableEmbeddedServletContainer container) {
		    
			ErrorPage error401Page = new ErrorPage(HttpStatus.UNAUTHORIZED, "/401.html");
			ErrorPage error403Page = new ErrorPage(HttpStatus.FORBIDDEN, "/403.html");
			ErrorPage error404Page = new ErrorPage(HttpStatus.NOT_FOUND, "/404.html");
			ErrorPage error500Page = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/500.html");

			container.addErrorPages(error401Page, error404Page, error403Page, error500Page);
			
			container.setSessionTimeout(1800);// 单位为S
		}

	}

}