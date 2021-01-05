package com.github.cesure.mortgagecalc.components

import com.github.cesure.mortgagecalc.formatCurrency
import com.github.cesure.mortgagecalc.formatPercentage
import com.github.cesure.mortgagecalc.model.Mortgage
import dev.fritz2.binding.RootStore
import dev.fritz2.dom.html.Div
import dev.fritz2.dom.html.RenderContext
import kotlinx.coroutines.flow.map
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

fun RenderContext.mortgageForm(): Div {
    val mortgageStore = object : RootStore<Mortgage>(
        Mortgage(
            amount = 100_000 * 100,
            annuity = 1_234 * 100,
            interestStart = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date,
            paymentDay = 30,
            interestRate = 175,
            interestOnlyMonths = 1,
        )
    ) {}

    return div("container", "mortgage-form") {
        div("form-row") {
            div("form-cell-half") {
                label {
                    `for`("amount")
                    +"Amount"
                }

                currencyInput(id = "amount", value = mortgageStore.data.map { it.amount.formatCurrency() }.asString())
            }

            div("form-cell-half") {
                label {
                    `for`("annuity")
                    +"Annuity"
                }
                currencyInput(id = "annuity", value = mortgageStore.data.map { it.annuity.formatCurrency() }.asString())
            }
        }

        div("form-row") {
            div("form-cell-half") {
                label {
                    `for`("interestStart")
                    +"Interest Start"
                }
                dateInput(id = "interestStart")
            }

            div("form-cell-half") {
                label {
                    `for`("paymentDay")
                    +"Payment Day"
                }
                numberInput(id = "paymentDay", value = mortgageStore.data.map { it.paymentDay }.asString())
            }
        }

        div("form-row") {
            div("form-cell-half") {
                label {
                    `for`("interestRate")
                    +"Interest Rate"
                }
                percentageInput(id = "interestRate", value = mortgageStore.data.map { it.interestRate.formatPercentage() })
            }

            div("form-cell-half") {
                label {
                    `for`("interestOnlyMonths")
                    +"Interest Only Months"
                }
                numberInput(id = "interestOnlyMonths", value = mortgageStore.data.map { it.interestOnlyMonths }.asString())
            }
        }

        div("form-row") {
            div("form-cell-full") {
                button {
                    +"Calculate"
                }
            }
        }
    }
}
