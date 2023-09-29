package com.ssu.commerce.core.error

open class SsuCommerceException(
    val httpStatus: Int,
    val errorCode: String?,
    override val message: String
) : RuntimeException()

class NotFoundException(message: String? = null, errorCode: String? = null) :
    SsuCommerceException(404, errorCode, message ?: "컨텐츠를 찾을 수 없습니다.")
