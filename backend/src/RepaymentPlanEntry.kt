package de.cesure

import java.math.BigDecimal
import java.time.LocalDate

data class RepaymentPlanEntry(val date: LocalDate, val repayment: Repayment, val amountLeft: BigDecimal)
