package com.ssu.commerce.core.web.exception

import com.fasterxml.jackson.databind.exc.InvalidFormatException
import com.ssu.commerce.core.error.SsuCommerceException
import org.springframework.core.Ordered.HIGHEST_PRECEDENCE
import org.springframework.core.annotation.Order
import org.springframework.http.HttpStatus
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.HttpMediaTypeNotSupportedException
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.MissingServletRequestParameterException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException
import javax.validation.ConstraintViolationException

@RestControllerAdvice
@Order(HIGHEST_PRECEDENCE)
class GeneralExceptionHandler {

    @ExceptionHandler(SsuCommerceException::class)
    fun handleSsuCommerceException(exception: SsuCommerceException): SsuCommerceException {
        return exception
    }

    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun handleBadRequestBody(exception: HttpMessageNotReadableException): SsuCommerceException {
        var message = "wrong request body"
        val cause = exception.cause
        if (cause is InvalidFormatException)
            message =
                "${cause.path[0].fieldName}는 ${cause.targetType.enumConstants.toList()}에 속하는 값이여야합니다. 입력된 값 : ${cause.value}"

        return SsuCommerceException(HttpStatus.BAD_REQUEST.value(), "System-002", message)
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleRequestValid(exception: MethodArgumentNotValidException): SsuCommerceException {
        val builder = StringBuilder()
        for (fieldError in exception.bindingResult.fieldErrors) {
            builder.append("[${fieldError.field}](은)는 ${fieldError.defaultMessage} 입력된 값: ${fieldError.rejectedValue}")
        }
        return SsuCommerceException(HttpStatus.BAD_REQUEST.value(), "System-003", builder.toString())
    }

    @ExceptionHandler(ConstraintViolationException::class)
    fun handleRequestValid(exception: ConstraintViolationException): SsuCommerceException {
        return SsuCommerceException(HttpStatus.BAD_REQUEST.value(), "System-004", "잘못된 데이터 요청입니다.")
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException::class)
    fun handleMethodNotSupport(exception: HttpRequestMethodNotSupportedException): SsuCommerceException {
        return SsuCommerceException(HttpStatus.BAD_REQUEST.value(), "System-005", "잘못된 요청입니다.")
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException::class)
    fun handleMethodArgumentTypeMismatch(exception: MethodArgumentTypeMismatchException): SsuCommerceException {
        return SsuCommerceException(
            HttpStatus.BAD_REQUEST.value(),
            "System-006",
            "잘못된 parameter 입니다. value: ${exception.value} "
        )
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException::class)
    fun handleHttpMediaTypeNotSupportedException(exception: HttpMediaTypeNotSupportedException): SsuCommerceException {
        return SsuCommerceException(
            HttpStatus.BAD_REQUEST.value(),
            "System-007",
            "지원하지 않는 MediaType 입니다. 요청된 type: ${exception.contentType}"
        )
    }

    @ExceptionHandler(MissingServletRequestParameterException::class)
    fun handleMissingServletRequestParameterException(exception: MissingServletRequestParameterException): SsuCommerceException {
        return SsuCommerceException(
            HttpStatus.BAD_REQUEST.value(),
            "System-008",
            "${exception.parameterName}이 누락되었습니다."
        )
    }
}
