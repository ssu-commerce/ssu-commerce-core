package com.ssu.commerce.core.monitoring.mdc

import org.slf4j.MDC
import org.slf4j.spi.MDCAdapter

object MDCStore {
    val requestMDC: MDCAdapter = MDC.getMDCAdapter()
    val METHOD: String = "REQUEST_METHOD"
    val URI: String = "REQUEST_URI"
    val HEADERS: String = "REQUEST_HEADER"
    val PARAMS: String = "REQUEST_PARAMS"
    val BODY: String = "REQUEST_BODY"
}
