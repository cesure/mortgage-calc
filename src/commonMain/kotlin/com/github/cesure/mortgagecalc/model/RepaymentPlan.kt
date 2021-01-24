package com.github.cesure.mortgagecalc.model

import com.github.cesure.mortgagecalc.model.serialization.DecimalSerializer
import com.github.cesure.mortgagecalc.model.serialization.LocalDateSerializer
import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable

typealias RepaymentPlan = List<RepaymentPlanEntry>

fun repaymentPlan(vararg payments: RepaymentPlanEntry): RepaymentPlan = payments.asList()
fun repaymentPlan(vararg payments: Pair<Transaction, Decimal>): RepaymentPlan =
    repaymentPlan(*payments.map { RepaymentPlanEntry(it.first, it.second) }.toTypedArray())

@Serializable
data class RepaymentPlanEntry(
    val transaction: Transaction,
    @Serializable(DecimalSerializer::class) val balanceAfter: Decimal,
)

@Serializable
data class Transaction(
    val type: TransactionType,
    @Serializable(LocalDateSerializer::class) val date: LocalDate,
    @Serializable(DecimalSerializer::class) val amount: Decimal,
)

@Serializable
enum class TransactionType {
    DEPOSIT,
    INTEREST,
    PAYOUT,
}
