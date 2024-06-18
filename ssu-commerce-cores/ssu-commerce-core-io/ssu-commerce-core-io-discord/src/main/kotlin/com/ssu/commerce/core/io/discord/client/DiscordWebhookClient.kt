package com.ssu.commerce.core.io.discord.client

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.multipart.MultipartFile

@FeignClient("discord-webhook", url = "\${discord.webhook}")
interface DiscordWebhookClient {
    @PostMapping
    fun sendMessage(@RequestBody req: PostWebhookRequest)

    @PostMapping
    fun sendEmbedMessage(@RequestBody req: PostEmbedWebhookRequest)

    @PostMapping(consumes = [MULTIPART_FORM_DATA_VALUE])
    fun attachFile(@RequestPart(value = "file") file: MultipartFile)
}

data class PostWebhookRequest(
    val content: String
)

data class PostEmbedWebhookRequest(
    val embeds: List<EmbedFields>
)

data class EmbedFields(
    val fields: List<Field>,
    val color: String? = null,
)

data class Field(
    val name: String,
    val value: String,
    val inline: Boolean? = null
)

val DISCORD_RED: String = "14177041"
