package com.ssu.commerce.core.data.configs

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile

@Profile("!test")
@Configuration
class DataSourceProperties {
    @Value("\${dataSource}")
    lateinit var dataSource: String
    @Value("\${userId}")
    lateinit var userId: String
    @Value("\${password}")
    lateinit var password: String
    @Value("\${driverClassName}")
    lateinit var driverClassName: String
}
