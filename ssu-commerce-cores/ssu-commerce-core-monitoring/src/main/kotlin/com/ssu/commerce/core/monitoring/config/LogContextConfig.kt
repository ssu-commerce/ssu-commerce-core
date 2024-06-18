package com.ssu.commerce.core.monitoring.config

import ch.qos.logback.classic.LoggerContext
import com.ssu.commerce.core.io.discord.client.DiscordWebhookClient
import com.ssu.commerce.core.monitoring.appender.ReportAppender
import com.ssu.commerce.core.monitoring.filter.CollectRequestDataFilter
import com.ssu.commerce.core.monitoring.filter.MultiReadableHttpServletRequestFilter
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.InitializingBean
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class LogContextConfig(
    private val discordWebhookClient: DiscordWebhookClient
) : InitializingBean {
    override fun afterPropertiesSet() {
        val loggerContext = LoggerFactory.getILoggerFactory() as LoggerContext

        val reportAppender = ReportAppender(discordWebhookClient)
        reportAppender.context = loggerContext
        reportAppender.name = "reportAppender"
        reportAppender.start()

        loggerContext.getLogger("ROOT").addAppender(reportAppender)
    }

    @Bean
    fun multiReadableHttpServletRequestFilterRegistrationBean(): FilterRegistrationBean<MultiReadableHttpServletRequestFilter> {
        val registrationBean: FilterRegistrationBean<MultiReadableHttpServletRequestFilter> =
            FilterRegistrationBean<MultiReadableHttpServletRequestFilter>()
        val multiReadableHttpServletRequestFilter = MultiReadableHttpServletRequestFilter()
        registrationBean.filter = multiReadableHttpServletRequestFilter
        registrationBean.order = 1

        return registrationBean
    }

    @Bean
    fun collectRequestDataFilterRegistrationBean(): FilterRegistrationBean<CollectRequestDataFilter> {
        val registrationBean: FilterRegistrationBean<CollectRequestDataFilter> =
            FilterRegistrationBean<CollectRequestDataFilter>()
        val collectRequestDataFilter: CollectRequestDataFilter = CollectRequestDataFilter()
        registrationBean.filter = collectRequestDataFilter
        registrationBean.order = 2

        return registrationBean
    }
}
