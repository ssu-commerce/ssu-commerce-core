package com.ssu.commerce.coretest.configs

import com.ssu.commerce.core.configs.SwaggerDocsConfig
import org.springframework.context.annotation.Configuration

@Configuration
class SwaggerConfig : SwaggerDocsConfig {
    override val basePackage: String = "com.ssu.commerce.coretest"
    override val title: String = "Core"
    override val version: String = "test"
}
