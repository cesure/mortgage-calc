package com.github.cesure.mortgagecalc.model

import kotlinx.datetime.LocalDate

fun Mortgage.calculateRepaymentPlan(): RepaymentPlan {
    val dummyPayment = Payment(
        type = PaymentType.PAYOUT,
        date = LocalDate(2020, 1, 1),
        amount = this.totalAmount.times(-1),
        balanceAfter = this.totalAmount.times(-1),
    )
    return repaymentPlan(dummyPayment)
}
