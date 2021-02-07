package com.github.cesure.mortgagecalc.model

import com.github.cesure.mortgagecalc.model.serialization.DecimalSerializer
import com.github.cesure.mortgagecalc.model.serialization.LocalDateSerializer
import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable

@Serializable
sealed class Transaction {
    abstract val date: LocalDate
    abstract val amount: Decimal
}

@Serializable
data class Payout(
    @Serializable(LocalDateSerializer::class) override val date: LocalDate,
    @Serializable(DecimalSerializer::class) override val amount: Decimal,
) : Transaction()
