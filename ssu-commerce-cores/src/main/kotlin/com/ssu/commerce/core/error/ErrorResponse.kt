package com.ssu.commerce.core.error

data class ErrorResponse(val message: String) {
    // ToDo에러코드 정책을 어떻게 할지 논의해봐야함.
    // val errorCode = error.errorCode
    constructor(error: SsuCommerceException) : this(error.message)
}
