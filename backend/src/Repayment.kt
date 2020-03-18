package de.cesure

import java.math.BigDecimal
import java.time.LocalDate

data class RepaymentPlan(val entries: List<RepaymentPlanEntry>)

data class RepaymentPlanEntry(val date: LocalDate, val repayment: Repayment, val amountLeft: BigDecimal)

data class Repayment(
    val interestsPayment: BigDecimal? = BigDecimal.ZERO,
    val downPayment: BigDecimal? = BigDecimal.ZERO
)
