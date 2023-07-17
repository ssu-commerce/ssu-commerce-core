package com.ssu.commerce.core.configs

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.servers.Server
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import org.springframework.scheduling.annotation.EnableScheduling

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@Import(EnableSsuCommerceCoreConfiguration::class)
annotation class EnableSsuCommerceCore

@Configuration
@OpenAPIDefinition(servers = [Server(url = "/")])
@ComponentScan(basePackages = ["com.ssu.commerce.core"])
@EnableFeignClients
@EnableScheduling
class EnableSsuCommerceCoreConfiguration
