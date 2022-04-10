package com.ssu.commerce.core.security

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource

@Configuration
@PropertySource("classpath:/security/ssu-commerce-jwt.yml")
@ConfigurationProperties(prefix = "jwt")
class JwtProviderProperties {
    @Value("\${secret}")
    lateinit var secret: String
}