package com.ssu.commerce.core

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.cloud.openfeign.EnableFeignClients

@SpringBootTest(properties = ["spring.profiles.active:test"])
@EnableFeignClients
class CoreApplicationTests {

    @Test
    fun contextLoads() {
    }
}
