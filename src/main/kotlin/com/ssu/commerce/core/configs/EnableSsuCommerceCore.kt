package com.ssu.commerce.core.configs

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.servers.Server
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@Import(EnableSsuCommerceCoreConfiguration::class)
annotation class EnableSsuCommerceCore

@Configuration
@OpenAPIDefinition(servers = [Server(url = "/")])
@ComponentScan(basePackages = ["com.ssu.commerce.core"])
class EnableSsuCommerceCoreConfiguration
