package de.cesure

import java.math.*
import java.time.*
import java.util.*
import kotlin.test.*

class MortgageTest {

    private val dummyMortgage = AdjustableRateMortgage(
        amount = BigDecimal.ZERO,
        interestStart = LocalDate.of(2020, 1, 1),
        interestsOnlyMonths = 0,
        paymentDay = 1,
        annuity = BigDecimal.ZERO,
        interestRates = TreeMap()
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
                    paymentDay = it.paymentDay
                ).firstPaymentDate()
            )
        }
    }

    @Test
    fun `test payments`() {
        val interestStart = LocalDate.of(2020, 1, 24)
        val interestsOnlyMonth = 1

        AdjustableRateMortgage(
            amount = BigDecimal(83500),
            interestStart = interestStart,
            interestsOnlyMonths = interestsOnlyMonth,
            paymentDay = 30,
            annuity = BigDecimal("278.34"),
            interestRates = TreeMap(mapOf(interestStart to BigDecimal(2)))
        )
            .repaymentPlan()
            .entries
            .forEach { println(it) }
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
