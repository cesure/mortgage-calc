package de.cesure

import java.math.*
import java.time.*
import kotlin.test.*

class MortgageTest {

    private val dummyMortgage = Mortgage(
        amount = BigDecimal.ONE,
        interestStart = LocalDate.of(2020, 1, 1),
        interestOnlyMonths = 0,
        paymentDay = 1,
        annuity = BigDecimal.ONE,
        interestRate = BigDecimal.ONE
    )

    @Test
    fun `validate payment dates`() {
        val jan20 = YearMonth.of(2020, 1)
        val feb20 = YearMonth.of(2020, 2)
        val feb21 = YearMonth.of(2021, 2)
        val mar20 = YearMonth.of(2020, 3)
        val apr20 = YearMonth.of(2020, 4)

        val mortgagePayOnFirst = dummyMortgage.copy(
            interestStart = LocalDate.of(2020, 1, 1),
            paymentDay = 1
        )

        assertEquals(jan20.atDay(1), mortgagePayOnFirst.paymentDate(jan20))
        assertEquals(feb20.atDay(1), mortgagePayOnFirst.paymentDate(feb20))
        assertEquals(feb21.atDay(1), mortgagePayOnFirst.paymentDate(feb21))
        assertEquals(mar20.atDay(1), mortgagePayOnFirst.paymentDate(mar20))
        assertEquals(apr20.atDay(1), mortgagePayOnFirst.paymentDate(apr20))

        val mortgagePayOn28th = mortgagePayOnFirst.copy(
            paymentDay = 28
        )

        assertEquals(jan20.atDay(28), mortgagePayOn28th.paymentDate(jan20))
        assertEquals(feb20.atDay(28), mortgagePayOn28th.paymentDate(feb20))
        assertEquals(feb21.atDay(28), mortgagePayOn28th.paymentDate(feb21))
        assertEquals(mar20.atDay(28), mortgagePayOn28th.paymentDate(mar20))
        assertEquals(apr20.atDay(28), mortgagePayOn28th.paymentDate(apr20))

        val mortgagePayOn29th = mortgagePayOnFirst.copy(
            interestStart = LocalDate.of(2020, 1, 1),
            paymentDay = 29
        )

        assertEquals(jan20.atDay(29), mortgagePayOn29th.paymentDate(jan20))
        assertEquals(feb20.atDay(29), mortgagePayOn29th.paymentDate(feb20))
        assertEquals(feb21.atDay(28), mortgagePayOn29th.paymentDate(feb21))
        assertEquals(mar20.atDay(29), mortgagePayOn29th.paymentDate(mar20))
        assertEquals(apr20.atDay(29), mortgagePayOn29th.paymentDate(apr20))

        val mortgagePayOn30th = mortgagePayOnFirst.copy(
            interestStart = LocalDate.of(2020, 1, 1),
            paymentDay = 30
        )

        assertEquals(jan20.atDay(30), mortgagePayOn30th.paymentDate(jan20))
        assertEquals(feb20.atDay(29), mortgagePayOn30th.paymentDate(feb20))
        assertEquals(feb21.atDay(28), mortgagePayOn30th.paymentDate(feb21))
        assertEquals(mar20.atDay(30), mortgagePayOn30th.paymentDate(mar20))
        assertEquals(apr20.atDay(30), mortgagePayOn30th.paymentDate(apr20))

        val mortgagePayOn31st = mortgagePayOnFirst.copy(
            interestStart = LocalDate.of(2020, 1, 1),
            paymentDay = 31
        )

        assertEquals(jan20.atDay(31), mortgagePayOn31st.paymentDate(jan20))
        assertEquals(feb20.atDay(29), mortgagePayOn31st.paymentDate(feb20))
        assertEquals(feb21.atDay(28), mortgagePayOn31st.paymentDate(feb21))
        assertEquals(mar20.atDay(31), mortgagePayOn31st.paymentDate(mar20))
        assertEquals(apr20.atDay(30), mortgagePayOn31st.paymentDate(apr20))
    }

    @Test
    fun `validate first payment date`() {
        data class ValidationHelper(
            val interestStart: LocalDate,
            val paymentDay: Int,
            val expectedFirstPaymentDate: LocalDate
        )

        listOf(
            ValidationHelper(LocalDate.of(2020, 1, 1), 1, LocalDate.of(2020, 2, 1)),
            ValidationHelper(LocalDate.of(2020, 1, 2), 1, LocalDate.of(2020, 2, 1)),
            ValidationHelper(LocalDate.of(2020, 1, 1), 2, LocalDate.of(2020, 1, 2)),
            ValidationHelper(LocalDate.of(2020, 1, 20), 10, LocalDate.of(2020, 2, 10)),
            ValidationHelper(LocalDate.of(2020, 1, 20), 20, LocalDate.of(2020, 2, 20)),

            ValidationHelper(LocalDate.of(2020, 2, 1), 28, LocalDate.of(2020, 2, 28)),
            ValidationHelper(LocalDate.of(2020, 2, 1), 28, LocalDate.of(2020, 2, 28)),
            ValidationHelper(LocalDate.of(2020, 2, 1), 29, LocalDate.of(2020, 2, 29)),
            ValidationHelper(LocalDate.of(2020, 2, 1), 30, LocalDate.of(2020, 2, 29)),
            ValidationHelper(LocalDate.of(2020, 2, 1), 31, LocalDate.of(2020, 2, 29)),

            ValidationHelper(LocalDate.of(2021, 2, 1), 28, LocalDate.of(2021, 2, 28)),
            ValidationHelper(LocalDate.of(2021, 2, 1), 29, LocalDate.of(2021, 2, 28)),
            ValidationHelper(LocalDate.of(2021, 2, 1), 30, LocalDate.of(2021, 2, 28)),
            ValidationHelper(LocalDate.of(2021, 2, 1), 31, LocalDate.of(2021, 2, 28)),
            ValidationHelper(LocalDate.of(2021, 2, 28), 28, LocalDate.of(2021, 3, 28))
        ).forEach {
            assertEquals(
                it.expectedFirstPaymentDate,
                dummyMortgage.copy(
                    interestStart = it.interestStart,
                    paymentDay = it.paymentDay,
                    interestRate = BigDecimal.ONE
                ).firstPaymentDate()
            )
        }
    }

    @Test
    fun `verify example payment plan`() {
        val interestStart = LocalDate.of(2020, 1, 24)
        val interestOnlyMonth = 1

        val plan = Mortgage(
            amount = BigDecimal(83500),
            interestStart = interestStart,
            interestOnlyMonths = interestOnlyMonth,
            paymentDay = 30,
            annuity = BigDecimal("278.34"),
            interestRate = BigDecimal("0.02")
        ).repaymentPlan()

        assertEquals(418, plan.entries.size)
        assertTrue(plan.entries.last().amountLeft.compareTo(BigDecimal.ZERO) == 0)

        val repayment123 = Repayment(
            interestPayment = BigDecimal("107.81"),
            downPayment = BigDecimal("170.53")
        )
        val entry123 = RepaymentPlanEntry(
            date = LocalDate.of(2030, 4, 30),
            repayment = repayment123,
            amountLeft = BigDecimal("64518.18")
        )
        assertEquals(entry123, plan.entries[123])
    }

    @Test
    fun `annuity must be larger than zero`() {
        assertFailsWith(IllegalArgumentException::class) {
            dummyMortgage.copy(annuity = BigDecimal(-1))
        }
        assertFailsWith(IllegalArgumentException::class) {
            dummyMortgage.copy(annuity = BigDecimal.ZERO)
        }

        // should not fail
        dummyMortgage.copy(annuity = BigDecimal.ONE)
    }

    @Test
    fun `amount must be greater than zero`() {
        assertFailsWith(IllegalArgumentException::class) {
            dummyMortgage.copy(amount = BigDecimal(-1))
        }

        assertFailsWith(IllegalArgumentException::class) {
            dummyMortgage.copy(amount = BigDecimal.ZERO)
        }

        // should not fail
        dummyMortgage.copy(amount = BigDecimal.ONE)
    }

    @Test
    fun `interest only months must be greater or equal than zero`() {
        assertFailsWith(IllegalArgumentException::class) {
            dummyMortgage.copy(interestOnlyMonths = -1)
        }

        // should not fail
        dummyMortgage.copy(interestOnlyMonths = 0)

        // should not fail
        dummyMortgage.copy(interestOnlyMonths = 1)
    }

    @Test
    fun `payment day must be betweend 1 and 31`() {
        assertFailsWith(IllegalArgumentException::class) {
            dummyMortgage.copy(paymentDay = -1)
        }

        assertFailsWith(IllegalArgumentException::class) {
            dummyMortgage.copy(paymentDay = 0)
        }

        assertFailsWith(IllegalArgumentException::class) {
            dummyMortgage.copy(paymentDay = 32)
        }

        // should not fail
        dummyMortgage.copy(paymentDay = 1)

        // should not fail
        dummyMortgage.copy(interestOnlyMonths = 31)
    }

    @Test
    fun `interest rate must be greater than zero`() {
        assertFailsWith(IllegalArgumentException::class) {
            dummyMortgage.copy(interestRate = BigDecimal.ZERO)
        }
        assertFailsWith(IllegalArgumentException::class) {
            dummyMortgage.copy(interestRate = BigDecimal(-1))
        }
    }

    @Test
    fun `test 30E360 day counting`() {
        assertEquals(0, countDays30E360(LocalDate.of(2020, 2, 28), LocalDate.of(2020, 2, 28)))
        assertEquals(47, countDays30E360(LocalDate.of(2020, 1, 1), LocalDate.of(2020, 2, 18)))
        assertEquals(1128, countDays30E360(LocalDate.of(2016, 12, 31), LocalDate.of(2020, 2, 18)))
        assertEquals(1, countDays30E360(LocalDate.of(2020, 2, 28), LocalDate.of(2020, 2, 29)))
        assertEquals(3, countDays30E360(LocalDate.of(2020, 2, 28), LocalDate.of(2020, 3, 1)))
    }
}
