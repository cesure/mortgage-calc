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
    val interestRate: BigDecimal,
    val extraRepayments: List<Repayment>
) {

    private val extraRepaymentsSorted: Map<LocalDate, List<Repayment>>

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

        extraRepaymentsSorted = extraRepayments.groupBy { it.date }
    }

    val annuity: BigDecimal
        get() = if (_annuity != null && _annuity > BigDecimal.ZERO) {
            _annuity
        } else {
            ((downPaymentRate + interestRate) * amount)
                .divide(BigDecimal(12), 2, RoundingMode.HALF_UP)
        }

    val downPaymentRate: BigDecimal
        get() = if (_downPaymentRate != null && _downPaymentRate > BigDecimal.ZERO) {
            _downPaymentRate
        } else {
            (annuity * BigDecimal(12))
                .divide(amount, 2, RoundingMode.HALF_UP) -
                interestRate
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

fun Mortgage.repaymentPlan(): RepaymentPlan {
    val entries = sequence {
        var amountLeft = amount
        val months = generateSequence(YearMonth.from(interestStart)) {
            it.plusMonths(1)
        }

        months.forEachIndexed { monthIdx, currentMonth ->
            val interestDays = if (monthIdx == 0 && interestStart.dayOfMonth != 1) {
                countDays30E360(interestStart, currentMonth.atEndOfMonth())
            } else 30
            val interest = calculateInterest(interestDays, amountLeft)
            val downPayment = if (monthIdx >= interestOnlyMonths) {
                (annuity - interest).min(amountLeft)
            } else BigDecimal.ZERO
            amountLeft -= downPayment

            val entry = RepaymentPlanEntry(
                repayment = Repayment(paymentDate(currentMonth), interest, downPayment),
                amountLeft = amountLeft
            )
            yield(entry)

            if (amountLeft.equalsZero()) {
                return@sequence
            }
        }
    }
    return RepaymentPlan(entries.toList())
}

private fun Mortgage.calculateInterest(days: Int, amountLeft: BigDecimal) =
    (amountLeft * interestRate * days.toBigDecimal())
        .divide(360.toBigDecimal(), 2, RoundingMode.HALF_UP)

fun countDays30E360(from: LocalDate, to: LocalDate): Int {
    require(to >= from)

    val d1 = from.dayOfMonth.takeUnless { it == 31 } ?: 30
    val d2 = to.dayOfMonth.takeUnless { it == 31 } ?: 30
    return 360 * (to.year - from.year) + 30 * (to.monthValue - from.monthValue) + (d2 - d1)
}

private fun BigDecimal.equalsZero() = this.compareTo(BigDecimal.ZERO) == 0
