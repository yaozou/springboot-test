package com.yanwei.platform.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger 配置文件
 * @author luojianhong
 * @version $Id: SwaggerConfig.java, v 0.1 2017年11月10日 下午4:44:23 luojianhong Exp $
 * http://localhost/memeber/swagger-ui.html
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
	
    
    @Bean 
    public Docket createRestApi() {  
        return new Docket(DocumentationType.SWAGGER_2)  
                .apiInfo(apiInfo())  
                .select()  
                .apis(RequestHandlerSelectors.basePackage("com.yanwei.platform"))  
                .paths(PathSelectors.any())  
                .build();  
    }  

    @SuppressWarnings("deprecation")
    private ApiInfo apiInfo() {
        
        ApiInfo apiInfo = 
            new ApiInfo("太平惠汇API接口手册", 
            "此在线API手册为客户端用户提供开发参考", "1.0.0",
            "太平惠汇", "luojianhong@qq.com", 
            "太平惠汇", "#");

        return apiInfo;
    }

}