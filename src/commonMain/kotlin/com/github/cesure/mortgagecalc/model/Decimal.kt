package com.github.cesure.mortgagecalc.model

expect class Decimal {

    constructor(strVal: String)
    constructor(doubleVal: Double)
    constructor(intVal: Int)
    constructor(longVal: Long)
}

expect fun Decimal.toNumber(): Number
