package com.ssu.commerce.core

import com.ssu.commerce.vault.config.EnableSsuCommerceVault
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableSsuCommerceVault
class CoreApplication

fun main(args: Array<String>) {
    runApplication<CoreApplication>(*args)
}
