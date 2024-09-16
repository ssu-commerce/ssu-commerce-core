package com.ssu.commerce.core.io.config

import org.springframework.beans.factory.ObjectProvider
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.autoconfigure.http.HttpMessageConverters
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.converter.HttpMessageConverter
import java.util.stream.Collectors

@Configuration
@EnableFeignClients(basePackages = ["com.ssu.commerce"])
class FeignConfig {
    @Bean
    @ConditionalOnMissingBean
    fun messageConverters(converters: ObjectProvider<HttpMessageConverter<*>?>): HttpMessageConverters {
        return HttpMessageConverters(converters.orderedStream().collect(Collectors.toList()))
    }
}
