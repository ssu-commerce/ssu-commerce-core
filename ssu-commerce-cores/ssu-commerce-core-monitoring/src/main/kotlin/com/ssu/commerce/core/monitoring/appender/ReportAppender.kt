package com.ssu.commerce.core.monitoring.appender

import ch.qos.logback.classic.Level
import ch.qos.logback.classic.spi.ILoggingEvent
import ch.qos.logback.classic.spi.ThrowableProxy
import ch.qos.logback.core.UnsynchronizedAppenderBase
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.ssu.commerce.core.io.discord.client.DISCORD_RED
import com.ssu.commerce.core.io.discord.client.DiscordWebhookClient
import com.ssu.commerce.core.io.discord.client.EmbedFields
import com.ssu.commerce.core.io.discord.client.Field
import com.ssu.commerce.core.io.discord.client.PostEmbedWebhookRequest
import com.ssu.commerce.core.io.discord.client.PostWebhookRequest
import com.ssu.commerce.core.monitoring.filter.CollectRequestDataFilter.Companion.BODY
import com.ssu.commerce.core.monitoring.filter.CollectRequestDataFilter.Companion.HEADERS
import com.ssu.commerce.core.monitoring.filter.CollectRequestDataFilter.Companion.METHOD
import com.ssu.commerce.core.monitoring.filter.CollectRequestDataFilter.Companion.PARAMS
import com.ssu.commerce.core.monitoring.filter.CollectRequestDataFilter.Companion.URI
import com.ssu.commerce.core.monitoring.filter.CollectRequestDataFilter.Companion.requestMDC
import org.slf4j.MDC
import org.springframework.web.multipart.MultipartFile
import java.io.ByteArrayInputStream
import java.io.File
import java.io.InputStream
import java.time.Instant.ofEpochMilli
import java.time.LocalDateTime
import java.time.ZoneOffset.systemDefault
import java.time.format.DateTimeFormatter

class ReportAppender(
    private val discordWebhookClient: DiscordWebhookClient
) : UnsynchronizedAppenderBase<ILoggingEvent>() {
    private val objectMapper: ObjectMapper = ObjectMapper()

    override fun append(event: ILoggingEvent) {
        sendLog(event, Level.WARN)
        sendStackTrace(event)
    }

    private fun sendStackTrace(event: ILoggingEvent) {
        if (event.throwableProxy == null) return
        var stackTrace = "${(event.throwableProxy as ThrowableProxy).throwable}\n\t"
        stackTrace += event.throwableProxy.stackTraceElementProxyArray.joinToString("\n\t")

        discordWebhookClient.attachFile(StackTraceMultipartFile(ByteArrayInputStream(stackTrace.toByteArray())))
    }

    private fun sendLog(event: ILoggingEvent, thresholdLogLevel: Level) {
        if (event.level.isGreaterOrEqual(thresholdLogLevel)) {
            reportRequestInfo()
            val logTime = LocalDateTime.ofInstant(ofEpochMilli(event.timeStamp), systemDefault())
                .format(DateTimeFormatter.ISO_DATE_TIME)
            val logLevel = event.level
            val threadName = event.threadName
            val pid = ProcessHandle.current().pid()
            val loggerName = event.loggerName
            val message = event.formattedMessage
            discordWebhookClient.sendMessage(
                PostWebhookRequest("$logTime $logLevel $pid --- [$threadName] $loggerName : $message")
            )
        }
    }

    private fun reportRequestInfo() {
        requestMDC.get(URI)?.let {
            discordWebhookClient.sendEmbedMessage(
                PostEmbedWebhookRequest(
                    listOf(
                        EmbedFields(
                            listOf(
                                Field("METHOD", requestMDC.get(METHOD), true),
                                Field("URI", requestMDC.get(URI), true),
                                Field("HEADERS", jsonToMap(requestMDC.get(HEADERS)).toPrettyString()),
                                Field("PARAMS", jsonToMap(requestMDC.get(PARAMS)).toPrettyString()),
                                Field("BODY", requestMDC.get(BODY)),
                            ),
                            DISCORD_RED
                        )
                    )
                )
            )
            MDC.clear()
        }
    }

    private fun jsonToMap(json: String): Map<String, Any> =
        objectMapper.readValue(json, object : TypeReference<HashMap<String, Any>>() {})

    private fun Map<String, Any>.toPrettyString(): String =
        map { "${it.key} : ${it.value}" }.joinToString("\n")
}

class StackTraceMultipartFile(private val stream: InputStream) : MultipartFile {
    override fun getInputStream(): InputStream = stream

    override fun getName(): String = "stacktrace.txt"

    override fun getOriginalFilename(): String = "stacktrace.txt"

    override fun getContentType(): String = "application/octet-stream"

    override fun isEmpty(): Boolean = false

    override fun getSize(): Long = stream.available().toLong()

    override fun getBytes(): ByteArray = stream.readAllBytes()

    override fun transferTo(dest: File) = transferTo(dest.toPath())
}
