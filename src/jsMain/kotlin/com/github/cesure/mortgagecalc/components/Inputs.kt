package com.github.cesure.mortgagecalc.components

import com.github.cesure.mortgagecalc.model.Decimal
import com.github.cesure.mortgagecalc.model.Formats
import dev.fritz2.binding.SubStore
import dev.fritz2.binding.storeOf
import dev.fritz2.dom.html.Div
import dev.fritz2.dom.html.Input
import dev.fritz2.dom.html.RenderContext
import dev.fritz2.dom.values
import dev.fritz2.lenses.Lens
import kotlinx.coroutines.flow.map
import kotlinx.datetime.LocalDate

fun <T> RenderContext.currencyInput(
    id: String,
    store: SubStore<T, T, Decimal>
): Div = formattedInput(id, store, Formats.currency, Formats.decimal)

fun <T> RenderContext.dateInput(
    id: String,
    store: SubStore<T, T, LocalDate>
): Div = formattedInput(id, store, Formats.localDate) {
    type("date")
}

fun <T> RenderContext.percentageInput(
    id: String,
    store: SubStore<T, T, Decimal>
): Div = formattedInput(id, store, Formats.percentage, Formats.percentageWithoutSign)

fun <T> RenderContext.numberInput(
    id: String,
    store: SubStore<T, T, Int>,
    content: (Input.() -> Unit)? = null,
): Div = formattedInput(id, store, Formats.integer) {
    type("number")
    content?.let { it() }
}

private fun <T, S> RenderContext.formattedInput(
    id: String,
    store: SubStore<T, T, S>,
    defaultLens: Lens<S, String>,
    focusLens: Lens<S, String>? = null,
    content: (Input.() -> Unit)? = null,
): Div = div {
    label {
        `for`(id)
        +(id.toTitleCase())
    }

    div("input-decoration") {
        input(id = id) {
            content?.let { it() }

            val defaultStore = store.sub(defaultLens)

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

                changes.values() handledBy focusStore.update

                focuss.events.map { true } handledBy hasFocus.update
                blurs.events.map { false } handledBy hasFocus.update
            } else {
                value(defaultStore.data)
                changes.values() handledBy defaultStore.update
            }
        }
    }
}

fun RenderContext.transactionInput(id: String) {
    fieldset {
        legend {
            +(id.toTitleCase())
        }
        div("input-decoration combined-input") {
            div("combined-input-element") {
                input {
                    type("date")
                }
            }
            div("combined-input-element") {
                input {
                    type("number")
                }
            }
        }
    }
}

private fun String.toTitleCase() = this.mapIndexed { index: Int, c: Char ->
    if (index == 0) {
        return@mapIndexed "${c.toUpperCase()}"
    }

    if (c.toUpperCase() == c) {
        " $c"
    } else {
        "$c"
    }
}.joinToString("")
