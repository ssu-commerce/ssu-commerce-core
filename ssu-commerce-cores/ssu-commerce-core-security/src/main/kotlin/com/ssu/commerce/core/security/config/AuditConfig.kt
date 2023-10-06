package com.ssu.commerce.core.security.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.data.domain.AuditorAware
import org.springframework.security.core.context.SecurityContextHolder
import java.util.Optional

@Configuration
class AuditConfig {

    @Bean
    @Primary
    fun authorizedAuditorProvider(): AuditorAware<String> =
        AuditorAware { Optional.of(SecurityContextHolder.getContext().authentication?.name ?: "System") }
}
