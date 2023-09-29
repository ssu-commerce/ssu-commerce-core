package com.ssu.commerce.core.web.response

import com.ssu.commerce.core.error.ErrorResponse
import com.ssu.commerce.core.error.SsuCommerceException
import org.springframework.core.MethodParameter
import org.springframework.data.domain.Page
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.http.server.ServerHttpRequest
import org.springframework.http.server.ServerHttpResponse
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice

@RestControllerAdvice
class StandardResponseBodyAdvice : ResponseBodyAdvice<Any> {
    override fun beforeBodyWrite(
        body: Any?,
        returnType: MethodParameter,
        selectedContentType: MediaType,
        selectedConverterType: Class<out HttpMessageConverter<*>>,
        request: ServerHttpRequest,
        response: ServerHttpResponse
    ): Any? {
        return when (body) {
            is Page<*> -> PageResponse(body)
            is List<*> -> ListResponse(body)
            is SsuCommerceException -> body.setStatusAndReturn(response)
            else -> body
        }
    }

    override fun supports(returnType: MethodParameter, converterType: Class<out HttpMessageConverter<*>>): Boolean =
        true
}

private fun SsuCommerceException.setStatusAndReturn(response: ServerHttpResponse): ErrorResponse {
    response.setStatusCode(HttpStatus.valueOf(httpStatus))
    return ErrorResponse(this)
}

class PageResponse<T>(page: Page<T>) {
    val contents = page.content
    val page: PageInfo = PageInfo(page.size, page.totalElements, page.totalPages, page.number)

    data class PageInfo(val size: Int, val totalElements: Long, val totalPages: Int, val number: Int)
}

data class ListResponse(val contents: List<*>)
