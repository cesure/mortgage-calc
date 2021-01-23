package com.github.cesure.mortgagecalc.model

import com.github.cesure.mortgagecalc.model.serialization.DecimalSerializer
import com.github.cesure.mortgagecalc.model.serialization.LocalDateSerializer
import dev.fritz2.lenses.Lenses
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.serialization.Serializable

@Lenses
@Serializable
data class Mortgage(
    @Serializable(DecimalSerializer::class)
    val amount: Decimal = Decimal(intVal = 100_000),
    @Serializable(DecimalSerializer::class)
    val annuity: Decimal = Decimal(intVal = 1_000),
    @Serializable(LocalDateSerializer::class)
    val interestStart: LocalDate = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date,
    val paymentDay: Int = 31,
    @Serializable(DecimalSerializer::class)
    val interestRate: Decimal = Decimal("0.0175"),
    val interestOnlyMonths: Int = 0,
)
