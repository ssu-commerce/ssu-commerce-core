package com.ssu.commerce.core.configs

import com.ssu.commerce.core.security.JwtAuthenticationFilter
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
class SecurityConfig(
    private val jwtAuthenticationFilter: JwtAuthenticationFilter,
    private val urlPermission: UrlPermissionFilter
) : WebSecurityConfigurerAdapter() {
    override fun configure(http: HttpSecurity) {
        http.httpBasic().disable()
            .csrf().disable()
            .cors().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authorizeRequests()
            .apply { urlPermission.urlPermissions(this) }
            .and()
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java)
    }

    override fun configure(web: WebSecurity) {
        web.ignoring().antMatchers(
            "/swagger-ui.html",
            "/swagger-ui/**",
            "/v3/api-docs/**"
        )
    }
}

interface UrlPermissionFilter {
    fun urlPermissions(authorizeRequests: ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry): ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry =
        authorizeRequests
            .anyRequest().authenticated()
}

@Configuration
class DefaultUrlPermissionFilter : UrlPermissionFilter
