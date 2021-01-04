package com.github.cesure.mortgagecalc.model

import kotlinx.datetime.LocalDate

data class Mortgage(
    val amount: Long,
    val annuity: Long,
    val interestStart: LocalDate,
)
