package com.example.mss.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * packageName  : com.example.mss.configuration
 * fileName     : SwaggerConfiguration
 * auther       : imzero-tech
 * date         : 2025. 1. 11.
 * descriptuon  :
 * =======================================================
 * DATE         AUTHER              NOTE
 * -------------------------------------------------------
 * 2025. 1. 11.      imzero-tech             최초생성
 */
@Configuration
public class SwaggerConfiguration {
    @Bean
    public OpenAPI openAPI() {
        Info info = new Info()
                            .version("v1.0")
                            .title("MSS Report") //이름
                            .description("과제 리포트 API");
        return new OpenAPI().info(info);
    }
}
