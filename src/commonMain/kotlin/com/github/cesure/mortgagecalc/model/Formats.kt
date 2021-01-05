package com.github.cesure.mortgagecalc.model

import dev.fritz2.lenses.Lens
import dev.fritz2.lenses.format
import kotlinx.datetime.LocalDate

object Formats {

    val currency: Lens<Long, String> = format(
        { it.unformatCurrency() },
        { it.formatCurrency() }
    )

    val localDate: Lens<LocalDate, String> = format(
        { LocalDate.parse(it.trim()) },
        { it.toString() },
    )

    val integer: Lens<Int, String> = format(
        { it.replace(",", "").trim().toInt() },
        { it.toString().reversed().chunked(3).joinToString(separator = ".").reversed() }
    )

    val percentage: Lens<Long, String> = format(
        { it.unformatPercentage() },
        { it.formatPercentage() }
    )
}

fun Long.formatCurrency(): String = "${this.formatDecimal()} €"

fun String.unformatCurrency(): Long = this.replace("€", "").unformatDecimal()

fun Long.formatPercentage(): String = "${this.formatDecimal()} %"

fun String.unformatPercentage(): Long = this.replace("%", "").unformatDecimal()

private fun Long.formatDecimal(): String {
    val asString = this.toString()
    val integerPart = asString.dropLast(2)
        .padStart(1, '0')
        .reversed()
        .chunked(3)
        .joinToString(".")
        .reversed()
    val fractionalPart = asString.takeLast(2)
        .padStart(2, '0')
    return "$integerPart,$fractionalPart"
}

fun String.unformatDecimal(): Long {
    val (integerPart, fractionalPart) = this.trim().split(",")
    return integerPart.replace(".", "").toLong() * 100 +
            fractionalPart.replace(".", "").toLong()
}
