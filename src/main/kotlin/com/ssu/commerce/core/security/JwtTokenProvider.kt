package com.ssu.commerce.core.security

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.io.DecodingException
import io.jsonwebtoken.security.Keys
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Component
import java.util.Date
import javax.crypto.SecretKey
import javax.servlet.http.HttpServletRequest

@Component
class JwtTokenProvider(
    private val jwtProviderProperties: JwtProviderProperties
) {

    private val secretKey: SecretKey = Keys.hmacShaKeyFor(jwtProviderProperties.secret.toByteArray())

    fun generateToken(userId: String, roles: Set<UserRole>, tokenValidMilSecond: Long): JwtTokenDto {
        val expiredIn = Date().time + tokenValidMilSecond
        val token = Jwts.builder()
            .claim("id", userId)
            .claim("roles", roles)
            .setIssuedAt(Date())
            .setExpiration(Date(expiredIn))
            .signWith(secretKey, SignatureAlgorithm.HS256)
            .compact()

        return JwtTokenDto(token, expiredIn)
    }

    fun resolveToken(req: HttpServletRequest): Claims? {
        var token = req.getHeader("Authorization")
        token = when {
            token == null -> return null
            token.contains("Bearer") -> token.replace("Bearer ", "")
            else -> throw DecodingException("지원하지 않는 인증 프로토콜입니다.")
        }
        return token.getClaimsFromToken()
    }

    private fun String.getClaimsFromToken(): Claims =
        Jwts.parserBuilder()
            .setSigningKey(secretKey)
            .build()
            .parseClaimsJws(this)
            .body

    fun getAuthentication(claims: Claims): Authentication =
        UsernamePasswordAuthenticationToken(claims["id"], "", claims.getAuthorities())

    private fun Claims.getAuthorities(): Collection<GrantedAuthority> =
        get("roles", List::class.java).map { SimpleGrantedAuthority(it as String) }

    fun getUserIdFromToken(token: String): String = token.getClaimsFromToken()["id"] as String
}

class JwtTokenDto(
    val token: String,
    val expiredIn: Long
)
