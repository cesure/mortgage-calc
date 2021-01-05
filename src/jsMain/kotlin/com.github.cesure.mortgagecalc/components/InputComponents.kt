package com.github.cesure.mortgagecalc.components

import dev.fritz2.dom.html.Input
import dev.fritz2.dom.html.RenderContext
import kotlinx.coroutines.flow.Flow

fun RenderContext.currencyInput(baseClass: String? = null, id: String? = null, value: Flow<String>?): Input {
    return input(id = id) {
        value?.let { value(it) }
    }
}

fun RenderContext.dateInput(baseClass: String? = null, id: String? = null, value: Flow<String>?): Input {
    return input(id = id) {
        type("date")
        value?.let { value(it) }
    }
}

fun RenderContext.numberInput(baseClass: String? = null, id: String? = null, value: Flow<String>?): Input {
    return input(id = id) {
        this.type("number")
        value?.let { value(it) }
    }
}

fun RenderContext.percentageInput(baseClass: String? = null, id: String? = null, value: Flow<String>?): Input {
    return input(id = id) {
        value?.let { value(it) }

//        focuss handledBy SimpleHandler<Unit>()
//        changes.values() handledBy
    }
}

//val testLens: Lens<String, String> = format(
//    { "+$it" },
//    { "$it+" }
//)
