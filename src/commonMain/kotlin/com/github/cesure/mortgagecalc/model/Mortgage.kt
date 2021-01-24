package com.github.cesure.mortgagecalc.model

import com.github.cesure.mortgagecalc.model.serialization.DecimalSerializer
import dev.fritz2.lenses.Lenses
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.serialization.Serializable

@Lenses
@Serializable
data class Mortgage(
    @Serializable(DecimalSerializer::class) val totalAmount: Decimal = Decimal(defaultTotalAmount),
    @Serializable(DecimalSerializer::class) val annuity: Decimal = Decimal(defaultAnnuity),
    val paymentDay: Int = defaultPaymentDay,
    @Serializable(DecimalSerializer::class) val interestRate: Decimal = Decimal(defaultInterestRate),
) {
    companion object {
        const val defaultTotalAmount = 100_000
        const val defaultAnnuity = 1_000
        const val defaultPaymentDay = 31
        const val defaultInterestRate = "0.0175"

        val defaultClock: LocalDate
            get() = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
    }
}
