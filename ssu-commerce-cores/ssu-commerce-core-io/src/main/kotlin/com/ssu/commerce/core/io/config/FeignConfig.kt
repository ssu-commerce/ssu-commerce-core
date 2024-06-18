package com.ssu.commerce.core.io.config

import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.context.annotation.Configuration

@Configuration
@EnableFeignClients(basePackages = ["com.ssu.commerce"])
class FeignConfig
