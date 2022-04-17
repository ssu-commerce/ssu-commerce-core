package com.ssu.commerce.core.configs

import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import

@Target(AnnotationTarget.TYPE)
@Retention(AnnotationRetention.RUNTIME)
@Import(EnableSsuCommerceCoreConfiguration::class)
annotation class EnableSsuCommerceCore

@Configuration
@ComponentScan(basePackages = ["com.ssu.commerce.core"])
class EnableSsuCommerceCoreConfiguration
