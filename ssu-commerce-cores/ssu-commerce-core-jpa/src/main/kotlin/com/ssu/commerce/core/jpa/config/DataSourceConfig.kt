package com.ssu.commerce.core.jpa.config

import org.slf4j.LoggerFactory
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import javax.sql.DataSource

open class DataSourceConfig(
    val dataSourceProperties: AbstractDataSourceProperties
) {
    @Bean
    open fun dataSource(): DataSource =
        DataSourceBuilder.create()
            .driverClassName(dataSourceProperties.driverClassName)
            .url(dataSourceProperties.dataSource)
            .username(dataSourceProperties.userId)
            .password(dataSourceProperties.password)
            .build()
            .also {
                LoggerFactory.getLogger(DataSourceConfig::class.java)
                    .info("\uD83D\uDCC2 SSUCommerce ${dataSourceProperties.projectName} DataSource 접속이 되었습니다.")
            }
}

interface AbstractDataSourceProperties {
    var projectName: String
    var dataSource: String
    var userId: String
    var password: String
    var driverClassName: String
}
