
package com.ssu.commerce.core.security

import org.springframework.core.MethodParameter
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer

@Target(AnnotationTarget.VALUE_PARAMETER, AnnotationTarget.TYPE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
annotation class Authenticated

class AuthInfo(
    var userId: String,
    var roles: List<UserRole>
)

@Component
class UserInfoArgumentResolver : HandlerMethodArgumentResolver {
    override fun supportsParameter(parameter: MethodParameter): Boolean {
        return parameter.getParameterAnnotation(Authenticated::class.java) != null &&
            parameter.parameterType == AuthInfo::class.java
    }

    override fun resolveArgument(
        parameter: MethodParameter,
        mavContainer: ModelAndViewContainer?,
        webRequest: NativeWebRequest,
        binderFactory: WebDataBinderFactory?
    ): Any {
        val authenticationToken = SecurityContextHolder.getContext().authentication
        return AuthInfo(
            userId = authenticationToken.principal.toString(),
            roles = authenticationToken.authorities.map { UserRole.valueOf(it.authority) }
        )
    }
}
