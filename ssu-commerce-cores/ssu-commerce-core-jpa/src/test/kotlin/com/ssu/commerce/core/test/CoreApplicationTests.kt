package com.ssu.commerce.core.test

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(properties = ["spring.profiles.active:test"])
class CoreApplicationTests {

    @Test
    fun contextLoads() {
    }
}
