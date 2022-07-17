package com.ssu.commerce.core.client.api

import com.ssu.commerce.core.security.JwtTokenDto
import com.ssu.commerce.core.security.UserRole
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

@FeignClient("auth", url = "\${ssu-commerce-url.auth}")
interface AuthApiClient {
    @PostMapping("/sign-in")
    fun login(@RequestBody signInRequest: SignInRequest): SessionTokens
    @PostMapping("/refresh")
    fun refresh(@RequestBody refreshRequest: RefreshRequest): SessionTokens
}

data class SignInRequest(
    val id: String,
    val password: String,
    val userRole: UserRole
)

data class RefreshRequest(
    val accessToken: String,
    val refreshToken: String,
)

data class SessionTokens(
    val accessToken: JwtTokenDto,
    val refreshToken: JwtTokenDto
)
