package com.ssu.commerce.core.jpa.config

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
}

interface AbstractDataSourceProperties {
    var dataSource: String
    var userId: String
    var password: String
    var driverClassName: String
}
