package com.ssu.commerce.core.client.api

import feign.Response
import feign.codec.ErrorDecoder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.vault.annotation.VaultPropertySource
import org.springframework.vault.annotation.VaultPropertySources

@Configuration
@VaultPropertySources(
    VaultPropertySource(
        value = ["ssu-commerce-url/\${spring.profiles.active:dev}"],
        propertyNamePrefix = "ssu-commerce-url."
    )
)
class FeignCustomSettings {
    @Bean
    fun errorDecoder(): ErrorDecoder = SsuCommerceFeignErrorDecoder()
}

class SsuCommerceFeignErrorDecoder : ErrorDecoder {
    override fun decode(methodKey: String?, response: Response?): Exception {
        TODO("장애처리에 대한 고민이 필요")
    }
}
