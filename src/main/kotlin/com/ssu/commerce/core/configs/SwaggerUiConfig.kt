package com.ssu.commerce.core.configs

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.Operation
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.security.SecurityScheme
import org.springdoc.core.GroupedOpenApi
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component
import org.springframework.web.method.HandlerMethod

@Component
@EnableConfigurationProperties
class SwaggerUiConfig(
    @Value("\${springdoc.swagger-ui.base-package}") private val basePackage: String,
    @Value("\${springdoc.swagger-ui.title}") private val title: String,
    @Value("\${springdoc.swagger-ui.version}") private val version: String
) {
    @Bean
    fun usersGroup(): GroupedOpenApi =
        GroupedOpenApi.builder().group(title)
            .addOperationCustomizer { operation: Operation, _: HandlerMethod? ->
                operation.addSecurityItem(SecurityRequirement().addList("bearer-key"))
            }
            .addOpenApiCustomiser { openApi: OpenAPI -> openApi.info(Info().title("$title API").version(version)) }
            .packagesToScan(basePackage)
            .build()

    @Bean
    fun authenticationHeaderConfig(): OpenAPI =
        OpenAPI().components(
            Components().addSecuritySchemes(
                "bearer-key",
                SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")
            )
        )
}
