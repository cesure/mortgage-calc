package com.github.cesure.mortgagecalc.model

import com.github.cesure.mortgagecalc.model.serialization.DecimalSerializer
import com.github.cesure.mortgagecalc.model.serialization.LocalDateSerializer
import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable

typealias RepaymentPlan = List<Payment>

fun repaymentPlan(vararg payments: Payment) = listOf(payments)

@Serializable
data class Payment(
    val type: PaymentType,
    @Serializable(LocalDateSerializer::class) val date: LocalDate,
    @Serializable(DecimalSerializer::class) val amount: Decimal,
)

@Serializable
enum class PaymentType {
    DEPOSIT,
    INTEREST,
    PAYOUT,
}
