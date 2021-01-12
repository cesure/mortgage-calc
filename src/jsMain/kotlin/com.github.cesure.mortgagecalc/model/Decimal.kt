package com.github.cesure.mortgagecalc.model

@JsModule("big.js")
@JsNonModule
private external fun bigJs(raw: dynamic): Big

internal external class Big {

    fun round(dp: Int, rm: Int): Big
    fun toFixed(decimalPlaces: Int): String
}

actual class Decimal {

    internal val raw: Big

    internal constructor(raw: Big) {
        this.raw = raw
    }

    actual constructor(strVal: String) : this(raw = bigJs(strVal))

    override fun toString(): String {
        return raw.toString()
    }
}

actual fun Decimal.round(decimalPlaces: Int): Decimal {
    return Decimal(raw.round(decimalPlaces, 1)) // rm = 1 is ROUND_HALF_UP
}

actual fun Decimal.toFixed(decimalPlaces: Int): String {
    return raw.toFixed(decimalPlaces)
}
