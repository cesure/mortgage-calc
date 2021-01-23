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
    @Serializable(DecimalSerializer::class) val amount: Decimal = Decimal(defaultAmount),
    @Serializable(DecimalSerializer::class) val annuity: Decimal = Decimal(defaultAnnuity),
    @Serializable(LocalDateSerializer::class) val interestStart: LocalDate = defaultClock,
    val paymentDay: Int = defaultPaymentDay,
    @Serializable(DecimalSerializer::class) val interestRate: Decimal = Decimal(defaultInterestRate),
    val interestOnlyMonths: Int = defaultInterestOnlyMonths,
) {
    companion object {
        const val defaultAmount = 100_000
        const val defaultAnnuity = 1_000
        const val defaultPaymentDay = 31
        const val defaultInterestRate = "0.0175"
        const val defaultInterestOnlyMonths = 0

        val defaultClock: LocalDate
            get() = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
    }
}
