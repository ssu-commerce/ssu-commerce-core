package com.ssu.commerce.core.client.api

import org.slf4j.LoggerFactory
import org.springframework.boot.context.event.ApplicationStartedEvent
import org.springframework.context.annotation.Configuration
import org.springframework.context.event.EventListener
import java.time.Instant
import java.time.LocalDate.now
import java.time.LocalDateTime
import java.time.ZoneId

@Configuration
class ServerSessionTokenInitializer(
    private val authApiClient: AuthApiClient
) {
    @EventListener(ApplicationStartedEvent::class)
    fun login() {
        initTokenInfo(authApiClient.login(SignInRequest("string", "string")))
    }

    companion object {
        private lateinit var accessToken: String
        private lateinit var accessTokenExpiredIn: LocalDateTime
        private lateinit var refreshToken: String
        private lateinit var refreshTokenExpiredIn: LocalDateTime

        internal fun getAccessToken() = accessToken
        internal fun getRefreshToken() = refreshToken

        internal fun checkAccessTokenIsExpired() = accessTokenExpiredIn.isBefore(LocalDateTime.now())

        internal fun initTokenInfo(sessionTokens: SessionTokens) {
            accessToken = sessionTokens.accessToken.token
            accessTokenExpiredIn = long2LocalDateTime(sessionTokens.accessToken.expiredIn)
            refreshToken = sessionTokens.refreshToken.token
            refreshTokenExpiredIn = long2LocalDateTime(sessionTokens.refreshToken.expiredIn)
            LoggerFactory.getLogger(ServerSessionTokenInitializer::class.java)
                .info("\uD83D\uDD11 인증서버로부터 성공적으로 세션을 발급받았습니다. 만료일시={$accessTokenExpiredIn}")
        }

        private fun long2LocalDateTime(expiredIn: Long) =
            LocalDateTime.ofInstant(Instant.ofEpochMilli(expiredIn), ZoneId.systemDefault())
    }
}
