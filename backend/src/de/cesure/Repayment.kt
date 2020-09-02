package de.cesure

import java.math.BigDecimal
import java.time.LocalDate

data class RepaymentPlan(val entries: List<RepaymentPlanEntry>) {

    val totalAmountPaid = entries.sumOf {
        (it.repayment.downPayment ?: BigDecimal.ZERO) + (it.repayment.interestPayment ?: BigDecimal.ZERO)
    }

    val numberOfPayments = entries.size

    val lastPaymentDate = entries.last().date
}

data class RepaymentPlanEntry(val date: LocalDate, val repayment: Repayment, val amountLeft: BigDecimal)

data class Repayment(
    val interestPayment: BigDecimal? = BigDecimal.ZERO,
    val downPayment: BigDecimal? = BigDecimal.ZERO
)
