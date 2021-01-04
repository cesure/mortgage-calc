package com.github.cesure.mortgagecalc.components

import dev.fritz2.dom.html.Input
import dev.fritz2.dom.html.RenderContext

fun RenderContext.currencyInput(baseClass: String? = null, id: String? = null, content: (Input.() -> Unit)?): Input {
    return input(id = id) {
        content?.let { it() }
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

fun RenderContext.percentageInput(baseClass: String? = null, id: String? = null): Input {
    return input(id = id) {
        type("percentage")
    }
}
