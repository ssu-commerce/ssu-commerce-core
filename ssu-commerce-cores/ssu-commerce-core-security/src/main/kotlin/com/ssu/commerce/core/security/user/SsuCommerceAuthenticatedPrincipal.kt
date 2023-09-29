package com.ssu.commerce.core.security.user

import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.core.AuthenticatedPrincipal
import org.springframework.security.core.GrantedAuthority
import java.util.UUID

class SsuCommerceAuthenticationToken(
    val accessToken: String,
    val userId: UUID,
    val userName: String,
    authorities: Collection<GrantedAuthority>
) : AbstractAuthenticationToken(authorities) {
    override fun isAuthenticated(): Boolean = true
    override fun getCredentials(): String = accessToken

    override fun getPrincipal(): SsuCommerceAuthenticatedPrincipal =
        SsuCommerceAuthenticatedPrincipal(
            userId = userId,
            userName = userName,
            accessToken = accessToken,
            role = authorities.map { UserRole.valueOf(it.authority) },
        )
}

data class SsuCommerceAuthenticatedPrincipal(
    val userId: UUID,
    val accessToken: String,
    val role: List<UserRole>,
    private val userName: String,
) : AuthenticatedPrincipal {

    override fun getName(): String = userName
}
