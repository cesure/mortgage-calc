package com.github.cesure.mortgagecalc.components

import com.github.cesure.mortgagecalc.model.Formats
import dev.fritz2.binding.SubStore
import dev.fritz2.binding.storeOf
import dev.fritz2.dom.html.Input
import dev.fritz2.dom.html.RenderContext
import dev.fritz2.dom.values
import dev.fritz2.lenses.Lens
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

fun <T> RenderContext.currencyInput(
    id: String? = null,
    store: SubStore<T, T, Long>
): Input = formattedInput(id, store, Formats.currency, Formats.decimal)

fun <T, S> RenderContext.formattedInput(
    id: String? = null,
    store: SubStore<T, T, S>,
    defaultLens: Lens<S, String>,
    focusLens: Lens<S, String>
): Input =
    input(id = id) {
        console.log("render formattedInput")

        val defaultStore = store.sub(defaultLens)
        val focusStore = store.sub(focusLens)

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
