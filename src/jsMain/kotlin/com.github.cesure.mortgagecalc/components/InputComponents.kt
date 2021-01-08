package com.github.cesure.mortgagecalc.components

import com.github.cesure.mortgagecalc.model.Formats
import dev.fritz2.binding.SubStore
import dev.fritz2.binding.storeOf
import dev.fritz2.dom.html.Input
import dev.fritz2.dom.html.RenderContext
import dev.fritz2.dom.values
import dev.fritz2.lenses.Lens
import kotlinx.coroutines.flow.map
import kotlinx.datetime.LocalDate

fun <T> RenderContext.currencyInput(
    id: String? = null,
    store: SubStore<T, T, Long>
): Input = formattedInput(id, store, Formats.currency, Formats.decimal)

fun <T> RenderContext.dateInput(
    id: String? = null,
    store: SubStore<T, T, LocalDate>
): Input = formattedInput(id, store, Formats.localDate) {
    type("date")
}

fun <T> RenderContext.percentageInput(
    id: String? = null,
    store: SubStore<T, T, Long>
): Input = formattedInput(id, store, Formats.percentage, Formats.decimal)

fun <T> RenderContext.numberInput(
    id: String? = null,
    store: SubStore<T, T, Int>
): Input = formattedInput(id, store, Formats.integer) {
    type("number")
}

fun <T, S> RenderContext.formattedInput(
    id: String? = null,
    store: SubStore<T, T, S>,
    defaultLlens: Lens<S, String>,
    focusLens: Lens<S, String>? = null,
    content: (Input.() -> Unit)? = null,
): Input =
    input(id = id) {
        content?.let { it() }

        val defaultStore = store.sub(defaultLlens)

        if (focusLens != null) {
            val focusStore = store.sub(focusLens)
            val hasFocus = storeOf(false)
            hasFocus.data.render { showUnformatted ->
                if (showUnformatted) {
                    value(focusStore.data)
                } else {
                    value(defaultStore.data)
                }
            }

            changes.values() handledBy (focusStore).update

            focuss.events.map { true } handledBy hasFocus.update
            blurs.events.map { false } handledBy hasFocus.update
        } else {
            value(defaultStore.data)
            changes.values() handledBy defaultStore.update
        }
    }
