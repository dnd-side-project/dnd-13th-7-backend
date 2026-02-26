package com.moyeoit.global.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springdoc.core.customizers.OperationCustomizer;

@Configuration
public class SwaggerConfig {

    private static final String DEPRECATED_PACKAGE_PREFIX = "com.moyeoit.context.deprecated";

    @Bean
    public OpenAPI openAPI() {
        String schemeName = "bearerAuth";

        return new OpenAPI()
                .info(new Info()
                        .title("모여잇 API")
                        .version("v1")
                        .description("모여잇 백엔드 OpenAPI 문서"))
                .addSecurityItem(new SecurityRequirement().addList(schemeName))
                .components(new Components()
                        .addSecuritySchemes(schemeName,
                                new SecurityScheme()
                                        .name(schemeName)
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")));
    }

    @Bean
    public OperationCustomizer deprecatedOperationCustomizer() {
        return (operation, handlerMethod) -> {
            if (handlerMethod.getBeanType().getPackageName().startsWith(DEPRECATED_PACKAGE_PREFIX)) {
                operation.setDeprecated(true);
            }
            return operation;
        };
    }

}
