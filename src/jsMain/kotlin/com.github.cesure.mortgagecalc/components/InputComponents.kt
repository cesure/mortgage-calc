package com.github.cesure.mortgagecalc.components

import dev.fritz2.dom.html.Input
import dev.fritz2.dom.html.RenderContext
import kotlinx.coroutines.flow.Flow

fun RenderContext.currencyInput(baseClass: String? = null, id: String? = null, value: Flow<String>?): Input {
    return input(id = id) {
        value?.let { value(it) }
    }
}

fun RenderContext.dateInput(baseClass: String? = null, id: String? = null): Input {
    return input(id = id) {
        type("date")
    }
}

fun RenderContext.numberInput(baseClass: String? = null, id: String? = null): Input {
    return input(id = id) {
        this.type("number")
    }
}

fun RenderContext.percentageInput(baseClass: String? = null, id: String? = null, value: Flow<String>?): Input {
    return input(id = id) {
        type("percentage")
        value?.let { value(it) }
    }
}
