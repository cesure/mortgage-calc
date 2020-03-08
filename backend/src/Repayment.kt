package de.cesure

import java.math.BigDecimal

data class Repayment(
    val interestsPayment: BigDecimal? = BigDecimal.ZERO,
    val downPayment: BigDecimal? = BigDecimal.ZERO
)


