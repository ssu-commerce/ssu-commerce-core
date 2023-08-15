package com.ssu.commerce.core.exception

import org.springframework.http.HttpStatus

open class SsuCommerceException(
    val httpStatus: HttpStatus,
    val errorCode: String?,
    override val message: String
) : RuntimeException()

class NotFoundException(message: String? = null, errorCode: String? = null) :
    SsuCommerceException(HttpStatus.NOT_FOUND, errorCode, message ?: "컨텐츠를 찾을 수 없습니다.")
