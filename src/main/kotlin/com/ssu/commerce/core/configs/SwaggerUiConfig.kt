package com.ssu.commerce.core.configs

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.Operation
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.security.SecurityScheme
import org.springdoc.core.GroupedOpenApi
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component
import org.springframework.web.method.HandlerMethod

@Component
class SwaggerUiConfig(
    private val swaggerconfig: SwaggerDocsConfig
) {
    @Bean
    fun usersGroup(): GroupedOpenApi =
        GroupedOpenApi.builder().group(swaggerconfig.title)
            .addOperationCustomizer { operation: Operation, handlerMethod: HandlerMethod? ->
                operation.addSecurityItem(SecurityRequirement().addList("bearer-key"))
                operation
            }
            .addOpenApiCustomiser { openApi: OpenAPI -> openApi.info(Info().title("${swaggerconfig.title} API").version(swaggerconfig.version)) }
            .packagesToScan(swaggerconfig.basePackage)
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

interface SwaggerDocsConfig {
    val basePackage: String
    val title: String
    val version: String
}
