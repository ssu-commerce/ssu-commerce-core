package com.ssu.commerce.core

import com.ssu.commerce.vault.config.EnableSsuCommerceVault
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients

@SpringBootApplication
@EnableFeignClients
@EnableSsuCommerceVault
class CoreApplication

fun main(args: Array<String>) {
    runApplication<CoreApplication>(*args)
}
