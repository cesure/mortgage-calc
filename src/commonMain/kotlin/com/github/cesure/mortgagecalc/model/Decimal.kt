package com.github.cesure.mortgagecalc.model

expect class Decimal {

    constructor(strVal: String)
    constructor(intVal: Int)
}

expect fun Decimal.div(number: Int): Decimal
expect fun Decimal.round(decimalPlaces: Int): Decimal
expect fun Decimal.times(number: Int): Decimal
expect fun Decimal.toFixed(decimalPlaces: Int): String
