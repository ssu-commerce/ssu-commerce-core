package com.ssu.commerce.core.client.api

import com.ssu.commerce.core.security.UserRole
import feign.RequestInterceptor
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.scheduling.annotation.Scheduled
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

@Profile("!test")
@Configuration
class ServerSessionTokenManager(
    private val authApiClient: AuthApiClient,
    private val context: ApplicationContext
) {
    init {
        applicationName = context.getBeanNamesForAnnotation(SpringBootApplication::class.java)[0]
            .replace("Application", "-service")
    }

    /**
     * 임시 ID 패스워드 값. 추후 정책 논의시 결정.
     */
    fun login() {
        setTokenInfo(authApiClient.login(SignInRequest("server-service", "string", UserRole.ROLE_SERVER)))
    }

    fun refreshToken() {
        setTokenInfo(authApiClient.refresh(RefreshRequest(accessToken, refreshToken)))
    }

    @Scheduled(fixedDelay = 3500000)
    fun manageAccessToken() {
        if (!isInitialized)
            login()
        else
            refreshToken()
    }

    @Bean
    fun requestInterceptor(): RequestInterceptor =
        RequestInterceptor {
            if (it.url().equals("/sign-in")) return@RequestInterceptor

            it.header("Authorization", "Bearer ${getAccessToken()}")
            it.header("X-request-service", applicationName)
        }

    companion object {
        private lateinit var applicationName: String
        private lateinit var accessToken: String
        private lateinit var accessTokenExpiredIn: LocalDateTime
        private lateinit var refreshToken: String
        private lateinit var refreshTokenExpiredIn: LocalDateTime
        private var isInitialized: Boolean = false

        internal fun getAccessToken() = accessToken

        internal fun setTokenInfo(sessionTokens: SessionTokens) {
            accessToken = sessionTokens.accessToken.token
            accessTokenExpiredIn = long2LocalDateTime(sessionTokens.accessToken.expiredIn)
            refreshToken = sessionTokens.refreshToken.token
            refreshTokenExpiredIn = long2LocalDateTime(sessionTokens.refreshToken.expiredIn)
            LoggerFactory.getLogger(ServerSessionTokenManager::class.java)
                .info("\uD83D\uDD11 인증서버로부터 성공적으로 세션을 발급받았습니다. 만료일시={$accessTokenExpiredIn}")
            isInitialized = true
        }

        private fun long2LocalDateTime(expiredIn: Long) =
            LocalDateTime.ofInstant(Instant.ofEpochMilli(expiredIn), ZoneId.systemDefault())
    }
}
