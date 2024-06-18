package com.ssu.commerce.core.monitoring.filter

import org.apache.tomcat.util.http.fileupload.IOUtils
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.IOException
import javax.servlet.Filter
import javax.servlet.FilterChain
import javax.servlet.ReadListener
import javax.servlet.ServletException
import javax.servlet.ServletInputStream
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletRequestWrapper

class MultiReadableHttpServletRequestFilter : Filter {
    @Throws(IOException::class, ServletException::class)
    override fun doFilter(req: ServletRequest, res: ServletResponse, chain: FilterChain) {
        chain.doFilter(MultiReadableHttpServletRequest(req as HttpServletRequest), res)
    }
}

class MultiReadableHttpServletRequest(request: HttpServletRequest?) : HttpServletRequestWrapper(request) {
    private var copiedRequest: ByteArrayOutputStream? = null

    @Throws(IOException::class)
    override fun getInputStream(): ServletInputStream {
        if (copiedRequest == null) {
            copiedRequest = ByteArrayOutputStream()
            IOUtils.copy(super.getInputStream(), copiedRequest)
        }
        return CachedServletInputStream()
    }

    inner class CachedServletInputStream : ServletInputStream() {
        private val input = ByteArrayInputStream(copiedRequest!!.toByteArray())
        override fun isFinished(): Boolean = false
        override fun isReady(): Boolean = true
        override fun read(): Int = input.read()
        override fun setReadListener(listener: ReadListener) {
        }
    }
}
