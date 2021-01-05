package com.github.cesure.mortgagecalc.components

import com.github.cesure.mortgagecalc.MortgageStore
import com.github.cesure.mortgagecalc.formatCurrency
import com.github.cesure.mortgagecalc.formatPercentage
import dev.fritz2.dom.html.Div
import dev.fritz2.dom.html.RenderContext
import kotlinx.coroutines.flow.map

fun RenderContext.mortgageForm(): Div {
    return div("container", "mortgage-form") {
        div("form-row") {
            div("form-cell-half") {
                label {
                    `for`("amount")
                    +"Amount"
                }

                currencyInput(id = "amount", value = MortgageStore.data.map { it.amount.formatCurrency() }.asString())
            }

            div("form-cell-half") {
                label {
                    `for`("annuity")
                    +"Annuity"
                }
                currencyInput(id = "annuity", value = MortgageStore.data.map { it.annuity.formatCurrency() }.asString())
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
                numberInput(id = "paymentDay", value = MortgageStore.data.map { it.paymentDay }.asString())
            }
        }

        div("form-row") {
            div("form-cell-half") {
                label {
                    `for`("interestRate")
                    +"Interest Rate"
                }
                percentageInput(
                    id = "interestRate",
                    value = MortgageStore.data.map { it.interestRate.formatPercentage() })
            }

            div("form-cell-half") {
                label {
                    `for`("interestOnlyMonths")
                    +"Interest Only Months"
                }
                numberInput(
                    id = "interestOnlyMonths",
                    value = MortgageStore.data.map { it.interestOnlyMonths }.asString()
                )
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
