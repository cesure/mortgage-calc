package com.github.cesure.mortgagecalc.model

@JsModule("decimal.js")
@JsNonModule
internal external fun jsBig(raw: dynamic): Big

@JsName("Number")
internal external fun jsNumber(raw: dynamic): Number

internal external class Big {
    fun plus(other: Big): Big
    fun minus(other: Big): Big
    fun times(other: Big): Big
    fun div(other: Big): Big
    fun cmp(other: Big): Int
    fun eq(other: Big): Boolean
    fun round(dp: Int, rm: Int): Big
}

actual class Decimal {

    internal val raw: Big

    internal constructor(raw: Big) {
        this.raw = raw
    }

    constructor() : this(raw = jsBig(0))

    actual constructor(strVal: String) : this(raw = jsBig(strVal))

    actual constructor(doubleVal: Double) {
        check(!doubleVal.isNaN() && !doubleVal.isInfinite())
        raw = jsBig(doubleVal)
    }

    actual constructor(intVal: Int) : this(raw = jsBig(intVal))

    // JS does not support 64-bit integer natively.
    actual constructor(longVal: Long) : this(raw = jsBig(longVal.toString()))

    fun toLong(): Long {
        // JSNumber is double precision, so it cannot exactly represent 64-bit `Long`.
        return toString().toLong()
    }

    fun toDouble(): Double {
        return jsNumber(raw).toDouble()
    }
}

actual fun Decimal.toNumber(): Number {
    val rounded = raw.round(0, 0)

    return if (raw.minus(rounded).eq(jsBig(0))) {
        toLong()
    } else {
        toDouble()
    }
}
