package com.github.cesure.mortgagecalc.model

import com.github.cesure.mortgagecalc.model.serialization.DecimalSerializer
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

