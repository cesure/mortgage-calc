package com.github.cesure.mortgagecalc.components

import dev.fritz2.binding.Store
import dev.fritz2.binding.storeOf
import dev.fritz2.dom.html.Input
import dev.fritz2.dom.html.RenderContext
import dev.fritz2.dom.values
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

fun RenderContext.currencyInput(id: String? = null, value: Flow<String>?): Input {
    return input(id = id) {
        value?.let { value(it) }
    }
}

fun RenderContext.formattedInput(id: String? = null, defaultStore: Store<String>, focusStore: Store<String>): Input =
    input(id = id) {
        console.log("render formattedInput")
        val hasFocus = storeOf(false)

        hasFocus.data.render { showUnformatted ->
            console.log("render formattedInput.value")
            value(if (showUnformatted) focusStore.data else defaultStore.data)
        }

        focuss.events.map { true } handledBy hasFocus.update
        blurs.events.map { false } handledBy hasFocus.update
        changes.values() handledBy focusStore.update
    }

fun RenderContext.dateInput(id: String? = null, value: Flow<String>?): Input =
    input(id = id) {
        type("date")
        value?.let { value(it) }
    }

fun RenderContext.numberInput(id: String? = null, value: Flow<String>?): Input =
    input(id = id) {
        type("number")
        value?.let { value(it) }
    }

fun RenderContext.percentageInput(id: String? = null, value: Flow<String>?): Input =
    input(id = id) {
        value?.let { value(it) }
    }
