package com.github.cesure.mortgagecalc.model

import kotlinx.datetime.LocalDate

fun Mortgage.calculateRepaymentPlan(): RepaymentPlan {
    val dummyPayment = Payment(
        type = PaymentType.PAYOUT,
        date = LocalDate(2020, 1, 1),
        amount = this.amount
    )
    return repaymentPlan(dummyPayment)
}
