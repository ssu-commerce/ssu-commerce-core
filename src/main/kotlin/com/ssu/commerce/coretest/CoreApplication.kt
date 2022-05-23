package com.ssu.commerce.coretest

import com.ssu.commerce.core.configs.EnableSsuCommerceCore
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableSsuCommerceCore
class CoreApplication

fun main(args: Array<String>) {
    runApplication<CoreApplication>(*args)
}
