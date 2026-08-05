package com.gl.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/*
    Configuration Swagger / OpenAPI.
    Accessible sur : http://localhost:8080/<contexte>/swagger-ui/index.html
    Doc brute JSON : http://localhost:8080/<contexte>/v3/api-docs
 */
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI hackathonOpenAPI() {
        final String schemeName = "bearerAuth";
        return new OpenAPI()
                .info(new Info()
                        .title("Hackathon Platform API")
                        .description("API REST pour la gestion d'un hackathon : équipes, projets, évaluation, classement")
                        .version("v1.0"))
                .addSecurityItem(new SecurityRequirement().addList(schemeName))
                .components(new Components()
                        .addSecuritySchemes(schemeName, new SecurityScheme()
                                .name(schemeName)
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")));
    }
}
