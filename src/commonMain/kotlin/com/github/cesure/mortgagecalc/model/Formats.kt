package com.github.cesure.mortgagecalc.model

import dev.fritz2.lenses.Lens
import dev.fritz2.lenses.format
import kotlinx.datetime.LocalDate

object Formats {

    val currency: Lens<Decimal, String> = format(
        { it.parseDecimal(2) },
        { it.formatCurrency() }
    )

    val decimal: Lens<Decimal, String> = format(
        { it.parseDecimal(2) },
        { it.formatDecimal(2) }
    )

    val localDate: Lens<LocalDate, String> = format(
        { LocalDate.parse(it.trim()) },
        { it.toString() },
    )

    val integer: Lens<Int, String> = format(
        { it.replace(".", "").trim().toInt() },
        { it.toString().reversed().chunked(3).joinToString(separator = ".").reversed() }
    )

    val percentage: Lens<Decimal, String> = format(
        { it.parsePercentage() },
        { it.times(100).formatPercentage() }
    )

    val percentageWithoutSign: Lens<Decimal, String> = format(
        { it.parsePercentage() },
        { it.times(100).formatDecimal(2) }
    )
}

fun Decimal.formatCurrency(): String = "${this.formatDecimal(2)} €"

fun Decimal.formatDecimal(decimalPlaces: Int): String {
    val (integerPart, decimalPart) = this.toFixed(decimalPlaces).split(".")
    val integerPartFormatted = integerPart.reversed().chunked(3).joinToString(".").reversed()
    return "$integerPartFormatted,$decimalPart"
}

fun String.parseDecimal(decimalPlaces: Int): Decimal {
    val splitted = this.split(",")
    val integerPart = splitted.first().filter { it.isDigit() }.ifBlank { "0" }
    val decimalPart = splitted.getOrNull(1).orEmpty().filter { it.isDigit() }.ifBlank { "0" }
    return Decimal("$integerPart.$decimalPart").round(decimalPlaces)
}

fun Decimal.formatPercentage(): String = "${this.formatDecimal(2)} %" // multiply 100

fun String.parsePercentage(): Decimal = this.parseDecimal(2).div(100)

private fun Char.isDigit(): Boolean = this in '0'..'9'
