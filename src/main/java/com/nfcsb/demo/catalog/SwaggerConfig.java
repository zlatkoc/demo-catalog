package com.nfcsb.demo.catalog;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket api() {
        ArrayList<Parameter> globalOperationParameters = new ArrayList<>();
        ArrayList<SecurityScheme> securitySchemes = new ArrayList<>();
        securitySchemes.add(new ApiKey("X-Token", "X-Token", "header"));
        securitySchemes.add(new BasicAuth("basic"));

        return new Docket(DocumentationType.SWAGGER_2)
                .useDefaultResponseMessages(false)
 //               .globalOperationParameters(globalOperationParameters)
                .securitySchemes(securitySchemes)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.nfcsb.demo.catalog"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        ApiInfo apiInfo = new ApiInfo(
                "My REST API",
                "Some custom description of API.",
                "API TOS",
                "Terms of service",
                contact(),
                "License of API",
                "API license URL");
        return apiInfo;
    }

    private Contact contact() {
        Contact contact = new Contact("zc", "http://www.google.com", "zc@zc.com");
        return contact;
    }
}