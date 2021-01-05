package com.github.cesure.mortgagecalc.model

import dev.fritz2.lenses.Lenses
import kotlinx.datetime.LocalDate

@Lenses
data class Mortgage(
    val amount: Long,
    val annuity: Long,
    val interestStart: LocalDate,
    val paymentDay: Int,
    val interestRate: Long,
    val interestOnlyMonths: Int,
)
