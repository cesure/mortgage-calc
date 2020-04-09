package de.cesure

import java.math.*
import java.time.*
import java.util.*
import kotlin.math.*

sealed class Mortgage {
    abstract val amount: BigDecimal
    abstract val interestStart: LocalDate
    abstract val interestOnlyMonths: Int
    abstract val paymentDay: Int
    abstract val annuity: BigDecimal
}

data class AdjustableRateMortgage(
    override val amount: BigDecimal,
    override val interestStart: LocalDate,
    override val interestOnlyMonths: Int,
    override val paymentDay: Int,
    override val annuity: BigDecimal,
    val interestRates: TreeMap<LocalDate, BigDecimal>
) : Mortgage() {

    init {
        require(annuity > BigDecimal.ZERO) {
            "Annuity must be greater than zero!"
        }
    }
}

fun Mortgage.interestRates(from: LocalDate, to: LocalDate): SortedMap<LocalDate, BigDecimal> {
    return when (this) {
        is AdjustableRateMortgage -> sortedMapOf(
            interestRates.entries.singleOrNull()?.toPair()
                ?: throw NotImplementedError("Adjustable rates are not implemented yet")
        )
    }
}

fun Mortgage.paymentDate(month: YearMonth): LocalDate = month.atDay(min(month.lengthOfMonth(), paymentDay))

fun Mortgage.firstPaymentDate(): LocalDate {
    val firstMonth = YearMonth.from(interestStart)
    val firstMonthPaymentDate = this.paymentDate(firstMonth)

    return if (firstMonthPaymentDate > interestStart) {
        firstMonthPaymentDate
    } else {
        this.paymentDate(firstMonth.plusMonths(1))
    }
}

fun Mortgage.nextPaymentDate(currentDate: LocalDate) = this.paymentDate(YearMonth.from(currentDate).plusMonths(1))

fun Mortgage.paymentDays(): Sequence<LocalDate> = generateSequence(this.firstPaymentDate()) {
    this.nextPaymentDate(it)
}

fun Mortgage.repaymentPlan(): RepaymentPlan {
    val entries = sequence {
        var amountLeft = amount
        var currentFrom = this@repaymentPlan.interestStart
        this@repaymentPlan.paymentDays().forEachIndexed { i, currentTo ->
            val interestRate = interestRates(currentFrom, currentTo).values.singleOrNull()
                ?: throw NotImplementedError("Adjustable rates are not implemented yet")
            val days = if (i == 0) {
                countDays30E360(currentFrom, currentTo)
            } else 30

            val interest = (amountLeft * interestRate * days.toBigDecimal())
                .divide(360.toBigDecimal(), 2, RoundingMode.HALF_UP)
            val downPayment = if (i >= interestOnlyMonths) {
                (annuity - interest).min(amountLeft)
            } else BigDecimal.ZERO
            amountLeft -= downPayment

            val entry = RepaymentPlanEntry(
                date = currentTo,
                repayment = Repayment(interest, downPayment),
                amountLeft = amountLeft
            )
            yield(entry)

            if (amountLeft.equalsZero()) {
                return@sequence
            }
            currentFrom = currentTo
        }
    }
    return RepaymentPlan(entries.toList())
}

fun countDays30E360(from: LocalDate, to: LocalDate): Int {
    require(to >= from)

    val d1 = from.dayOfMonth.takeUnless { it == 31 } ?: 30
    val d2 = to.dayOfMonth.takeUnless { it == 31 } ?: 30
    return 360 * (to.year - from.year) + 30 * (to.monthValue - from.monthValue) + (d2 - d1)
}

private fun BigDecimal.equalsZero() = this.compareTo(BigDecimal.ZERO) == 0
