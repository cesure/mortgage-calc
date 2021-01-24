package com.github.cesure.mortgagecalc.model

@JsModule("big.js")
@JsNonModule
private external fun bigJs(raw: dynamic): Big

internal external class Big {

    fun cmp(other: Decimal): Int
    fun div(number: Number): Big
    fun round(decimalPlaces: Int, roundingMode: Int): Big
    fun times(number: Number): Big
    fun toFixed(decimalPlaces: Int): String
}

actual class Decimal : Comparable<Decimal> {

    internal val raw: Big

    internal constructor(raw: Big) {
        this.raw = raw
    }

    actual constructor(strVal: String) : this(raw = bigJs(strVal))

    actual constructor(intVal: Int) : this(raw = bigJs(intVal))

    override fun compareTo(other: Decimal): Int {
        return raw.cmp(other)
    }

    override fun toString(): String {
        return raw.toString()
    }
}

actual fun Decimal.div(number: Int): Decimal {
    return Decimal(raw.div(number))
}

actual fun Decimal.round(decimalPlaces: Int): Decimal {
    return Decimal(raw.round(decimalPlaces, RoundingMode.ROUND_HALF_UP.value))
}

actual fun Decimal.times(number: Int): Decimal {
    return Decimal(raw.times(number))
}

actual fun Decimal.toFixed(decimalPlaces: Int): String {
    return raw.toFixed(decimalPlaces)
}

private enum class RoundingMode(val value: Int) {

    ROUND_DOWN(1),
    ROUND_HALF_UP(2),
    ROUND_HALF_EVEN(3),
    ROUND_UP(4),
}
