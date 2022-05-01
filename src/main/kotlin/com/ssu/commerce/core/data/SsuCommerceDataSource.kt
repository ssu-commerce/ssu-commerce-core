package com.ssu.commerce.core.data

import com.ssu.commerce.core.data.configs.DataSourceProperties
import org.slf4j.LoggerFactory
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.context.annotation.Profile
import javax.sql.DataSource

@Profile("!test")
@Configuration
class SsuCommerceDataSource(
    private val dataSourceProperties: DataSourceProperties
) {

    @Primary
    @Bean
    fun dataSource(): DataSource =
        DataSourceBuilder.create()
            .driverClassName(dataSourceProperties.driverClassName)
            .url(dataSourceProperties.dataSource)
            .username(dataSourceProperties.userId)
            .password(dataSourceProperties.password)
            .build()
            .also { LoggerFactory.getLogger(SsuCommerceDataSource::class.java).info("\uD83D\uDCC2 SSUCommerce DataSource 접속이 되었습니다.") }
}
