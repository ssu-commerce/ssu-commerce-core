package com.ssu.commerce.coretest

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/test")
class TestController {
    @GetMapping
    fun test() = "test"

    @GetMapping("/2")
    fun test2() = "test2"
}
