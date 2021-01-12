package com.github.cesure.mortgagecalc.model

@JsModule("big.js")
@JsNonModule
private external fun bigJs(raw: dynamic): Big

internal external class Big {

    fun toFixed(decimalPlaces: Int): String
}

actual class Decimal {

    internal val raw: Big

    private constructor(raw: Big) {
        this.raw = raw
    }

    actual constructor(strVal: String) : this(raw = bigJs(strVal))

    override fun toString(): String {
        return raw.toString()
    }
}

actual fun Decimal.toFixed(decimalPlaces: Int): String {
    return raw.toFixed(decimalPlaces)
}
