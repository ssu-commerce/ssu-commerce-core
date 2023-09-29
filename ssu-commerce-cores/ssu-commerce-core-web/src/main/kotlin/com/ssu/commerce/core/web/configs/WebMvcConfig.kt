package com.ssu.commerce.core.web.configs

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebMvcConfig : WebMvcConfigurer {

    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/**")
            .allowedOrigins(
                "http://localhost:3000",
                "https://ssu-commerce.github.io"
            )
            .allowedMethods("*")
    }

    // override fun addArgumentResolvers(resolvers: MutableList<HandlerMethodArgumentResolver>) {
    //     resolvers.add(userInfoArgumentResolver)
    // }
}
