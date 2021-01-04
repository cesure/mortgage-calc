package com.github.cesure.mortgagecalc.model

import kotlinx.datetime.LocalDate

data class Mortgage(
    val amount: Double,
    val interestStart: LocalDate,
)
