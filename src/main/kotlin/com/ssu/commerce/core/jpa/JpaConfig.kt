package com.ssu.commerce.core.jpa

import com.ssu.commerce.core.exception.SsuCommerceException
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.domain.AuditorAware
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.http.HttpStatus
import org.springframework.security.core.context.SecurityContextHolder
import java.util.Optional

@Configuration
@EnableJpaAuditing
class JpaConfig {
    @Bean
    // TODO: Error code 설정
    fun auditorProvider(): AuditorAware<String> =
        AuditorAware {
            Optional.of(
                SecurityContextHolder.getContext().authentication?.name
                    ?: throw SsuCommerceException(HttpStatus.FORBIDDEN, null, "Not exist authentication")
            )
        }
}
