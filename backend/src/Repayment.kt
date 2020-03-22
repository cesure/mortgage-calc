package de.cesure

import java.math.*
import java.time.*

data class RepaymentPlan(val entries: List<RepaymentPlanEntry>)

data class RepaymentPlanEntry(val date: LocalDate, val repayment: Repayment, val amountLeft: BigDecimal)

data class Repayment(
    val interestPayment: BigDecimal? = BigDecimal.ZERO,
    val downPayment: BigDecimal? = BigDecimal.ZERO
)
