package com.github.moruke.wall.bootstrap.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket createRestApi() {

        return new Docket(DocumentationType.SWAGGER_2)

                .apiInfo(apiInfo())

                .select()

                .apis(RequestHandlerSelectors.basePackage("com.github.moruke.wall.bootstrap.api.auth"))

                .paths(PathSelectors.any())

                .build();

    }

    private ApiInfo apiInfo() {

        return new ApiInfoBuilder()

                .title("Wall-Bootstrap API Documentation")

                .version("1.0.0-SNAPSHOT")

                .description("Wall-Bootstrap API Documentation")

                .build();

    }

}
