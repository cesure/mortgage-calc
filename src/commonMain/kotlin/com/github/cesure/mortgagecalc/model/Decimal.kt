package com.github.cesure.mortgagecalc.model

expect class Decimal {

    constructor(strVal: String)
}

expect fun Decimal.toFixed(decimalPlaces: Int): String
