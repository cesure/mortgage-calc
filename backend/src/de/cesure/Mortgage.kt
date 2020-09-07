package de.cesure

import java.math.BigDecimal
import java.math.RoundingMode
import java.time.LocalDate
import java.time.YearMonth
import kotlin.math.min

data class Mortgage(
    val amount: BigDecimal,
    val interestStart: LocalDate,
    val interestOnlyMonths: Int,
    val paymentDay: Int,
    private val _annuity: BigDecimal? = null,
    private val _downPaymentRate: BigDecimal? = null,
    val interestRate: BigDecimal
) {

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
        val annuityGiven = _annuity != null && _annuity > BigDecimal.ZERO
        val downPaymentRateGiven = _downPaymentRate != null && _downPaymentRate > BigDecimal.ZERO
        require(annuityGiven xor downPaymentRateGiven) {
            "Either annuity or down payment rate must be given and greater zero!"
        }
        require(interestRate > BigDecimal.ZERO) {
            "Interest rate must be greater than zero!"
        }
    }

    val annuity: BigDecimal
        get() = if (_annuity != null && _annuity > BigDecimal.ZERO) {
            _annuity
        } else {
            ((downPaymentRate + interestRate) * amount)
                .divide(BigDecimal(12), 2, RoundingMode.HALF_UP)
        }

    val downPaymentRate: BigDecimal
        get() {
            return if (_downPaymentRate != null && _downPaymentRate > BigDecimal.ZERO) {
                _downPaymentRate
            } else {
                (annuity * BigDecimal(12))
                    .divide(amount, 2, RoundingMode.HALF_UP) -
                    interestRate
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
            val interestDays = if (i == 0 && currentFrom.dayOfMonth != 1) {
                countDays30E360(currentFrom, currentTo) // calculate part of month
            } else 30 // German banks use 30 days for a whole month

            val interest = (amountLeft * interestRate * interestDays.toBigDecimal())
                .divide(360.toBigDecimal(), 2, RoundingMode.HALF_UP)
            val downPayment = if (i >= interestOnlyMonths) {
                (annuity - interest).min(amountLeft)
            } else BigDecimal.ZERO
            amountLeft -= downPayment

            val entry = RepaymentPlanEntry(
                repayment = Repayment(currentTo, interest, downPayment),
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
