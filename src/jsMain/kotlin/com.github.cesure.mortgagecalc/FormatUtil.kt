package com.github.cesure.mortgagecalc

fun Long.formatCurrency(): String = "${this.formatDecimal()} €"

fun Long.formatPercentage(): String = "${this.formatDecimal()} %"

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
