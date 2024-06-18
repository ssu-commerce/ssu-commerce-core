package com.ssu.commerce.core.monitoring.filter

import com.fasterxml.jackson.databind.ObjectMapper
import com.ssu.commerce.core.monitoring.mdc.MDCStore.BODY
import com.ssu.commerce.core.monitoring.mdc.MDCStore.HEADERS
import com.ssu.commerce.core.monitoring.mdc.MDCStore.METHOD
import com.ssu.commerce.core.monitoring.mdc.MDCStore.PARAMS
import com.ssu.commerce.core.monitoring.mdc.MDCStore.URI
import com.ssu.commerce.core.monitoring.mdc.MDCStore.requestMDC
import org.slf4j.MDC
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.util.stream.Collectors
import javax.servlet.Filter
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest

class CollectRequestDataFilter : Filter {
    private val objectMapper: ObjectMapper = ObjectMapper()

    @Throws(IOException::class, ServletException::class)
    override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {
        request as HttpServletRequest
        val headers = getHeader(request)
        val parameters = getParameter(request)
        val body = getBody(request)

        requestMDC.run {
            put(METHOD, request.method)
            put(URI, request.requestURI)
            put(HEADERS, objectMapper.writeValueAsString(headers))
            put(PARAMS, objectMapper.writeValueAsString(parameters))
            put(BODY, objectMapper.writeValueAsString(body))
        }

        try {
            chain.doFilter(request, response)
        } finally {
            MDC.clear()
        }
    }

    private fun getHeader(request: HttpServletRequest): Map<String, String> =
        request.headerNames.toList().associateWith { request.getHeader(it) }

    private fun getParameter(request: HttpServletRequest): MutableMap<String, Array<String>>? {
        return request.parameterMap
    }

    private fun getBody(request: HttpServletRequest): String =
        BufferedReader(InputStreamReader(request.inputStream)).lines().collect(Collectors.joining())
}
