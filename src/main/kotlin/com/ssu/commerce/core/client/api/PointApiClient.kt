package com.ssu.commerce.core.client.api

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import java.math.BigDecimal
import java.time.LocalDateTime

@FeignClient("auth", url = "\${ssu-commerce-url.point}")
interface PointApiClient {

    @PostMapping("/point/transaction")
    fun requestTransaction(
        @RequestBody req: PointTransactionRequest
    ): PointTransactionResponse
}

data class PointTransactionRequest(
    val accountId: Long,
    val transactionId: Long,
    val transactionType: TransactionType,
    val transactionAmount: BigDecimal,
    val description: String
)

data class PointTransactionResponse(
    val pointTransactionId: Long,
    val accountId: Long,
    val transactionId: Long,
    val transactionType: TransactionType,
    val transactionAmount: BigDecimal,
    val afterBalance: BigDecimal,
    val description: String,
    val approvedAt: LocalDateTime
)

enum class TransactionType(val plus: Boolean) {
    CHARGE(true),
    ORDER(false),
    REWARD(true)
}
