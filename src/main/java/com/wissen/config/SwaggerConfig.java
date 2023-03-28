package com.wissen.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Configuration for Swagger.
 *
 * @author Vishal Tomar
 */
@EnableSwagger2
@Configuration
public class SwaggerConfig {

    @Bean
    public Docket swaggerConfiguration(){
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .paths(PathSelectors.ant("/api/**"))
                .apis(RequestHandlerSelectors.basePackage("com.wissen"))
                .build()
                .apiInfo(new ApiInfo(
                        "Wissen Technology",
                        "Wissen office Visitors Details",
                        "1.0",
                        "Licence as per Wissen Technology",
                        new Contact("Wissen Technology", "https://www.wissen.com/", "test@wissen.com"),
                        "API Licence",
                        "https://www.wissen.com/privacy-policy-2/"
                ));
    }

}
