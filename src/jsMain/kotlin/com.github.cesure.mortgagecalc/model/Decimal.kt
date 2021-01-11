package com.github.cesure.mortgagecalc.model

@JsModule("big.js")
@JsNonModule
private external fun bigJs(raw: dynamic): Big

private external class Big {

    fun toFixed(decimalPlaces: Int): String
//    fun plus(other: Big): Big
//    fun minus(other: Big): Big
//    fun times(other: Big): Big
//    fun div(other: Big): Big
//    fun cmp(other: Big): Int
//    fun eq(other: Big): Boolean
//    fun round(dp: Int, rm: Int): Big
}

actual class Decimal {

    private val raw: Big

    private constructor(raw: Big) {
        this.raw = raw
    }

    constructor() : this(raw = bigJs(0))

    actual constructor(strVal: String) : this(raw = bigJs(strVal))

//    actual constructor(intVal: Int) : this(raw = bigJs(intVal))

//    actual constructor(longVal: Long) :this(raw = bigJs(longVal))

    override fun toString(): String {
        return raw.toFixed(2)
    }
}
