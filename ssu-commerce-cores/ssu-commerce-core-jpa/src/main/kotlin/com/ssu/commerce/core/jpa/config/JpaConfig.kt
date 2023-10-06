package com.ssu.commerce.core.jpa.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.domain.AuditorAware
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import java.util.Optional

@Configuration
@EnableJpaAuditing
class JpaConfig {
    @Bean
    fun auditorProvider(): AuditorAware<String> = AuditorAware { Optional.of("System") }
}
