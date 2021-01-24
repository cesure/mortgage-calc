package com.github.cesure.mortgagecalc.model

import kotlinx.datetime.LocalDate

fun Mortgage.calculateRepaymentPlan(): RepaymentPlan {
    val firstPayment = Payment(
        type = PaymentType.PAYOUT,
        date = LocalDate(2020, 1, 1),
        amount = this.totalAmount.times(-1),
    )
    return repaymentPlan(firstPayment to this.totalAmount.times(-1))
}
