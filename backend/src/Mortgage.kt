package de.cesure

import java.math.BigDecimal
import java.math.RoundingMode
import java.time.LocalDate
import java.time.YearMonth
import kotlin.math.min

sealed class Mortgage {
    abstract val amount: BigDecimal
    abstract val interestStart: LocalDate
    abstract val interestOnlyMonths: Int
    abstract val paymentDay: Int
    abstract val annuity: BigDecimal
    abstract val interestRate: BigDecimal
}

data class AdjustableRateMortgage(
    override val amount: BigDecimal,
    override val interestStart: LocalDate,
    override val interestOnlyMonths: Int,
    override val paymentDay: Int,
    override val annuity: BigDecimal,
    override val interestRate: BigDecimal
) : Mortgage() {

    init {
        require(amount > BigDecimal.ZERO) {
            "Amount must be greater than zero!"
        }
        require(interestOnlyMonths >= 0) {
            "Interest Only Months must be greater or equal than zero!"
        }
        require(paymentDay in 1..31) {
            "Payment Day must be between 1 and 31!"
        }
        require(annuity > BigDecimal.ZERO) {
            "Annuity must be greater than zero!"
        }
        require(interestRate > BigDecimal.ZERO) {
            "Interest rate must be greater than zero!"
        }
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
