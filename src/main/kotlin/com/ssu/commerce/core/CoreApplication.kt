package com.ssu.commerce.core

import com.ssu.commerce.vault.config.EnableSsuCommerceVault
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableFeignClients
@EnableSsuCommerceVault
@EnableScheduling
class CoreApplication

fun main(args: Array<String>) {
    runApplication<CoreApplication>(*args)
}
