package com.github.cesure.mortgagecalc.components

import com.github.cesure.mortgagecalc.MortgageStore
import com.github.cesure.mortgagecalc.model.Formats
import com.github.cesure.mortgagecalc.model.L
import dev.fritz2.dom.html.Div
import dev.fritz2.dom.html.RenderContext

fun RenderContext.mortgageForm(): Div {

    val amountStore = MortgageStore.sub(L.Mortgage.amount)
    val annuityStore = MortgageStore.sub(L.Mortgage.annuity)
    val interestStartStore = MortgageStore.sub(L.Mortgage.interestStart)

    val interestStart = MortgageStore.sub(L.Mortgage.interestStart + Formats.localDate)
    val interestRateStore = MortgageStore.sub(L.Mortgage.interestRate)
    val paymentDay = MortgageStore.sub(L.Mortgage.paymentDay + Formats.integer)
    val interestOnlyMonths = MortgageStore.sub(L.Mortgage.interestOnlyMonths + Formats.integer)

    return div("container", "mortgage-form") {
        div("form-row") {
            div("form-cell-half") {
                label {
                    `for`("amount")
                    +"Amount"
                }

                currencyInput("amount", amountStore)
            }

            div("form-cell-half") {
                label {
                    `for`("annuity")
                    +"Annuity"
                }
                currencyInput("annuity", annuityStore)
            }
        }

        div("form-row") {
            div("form-cell-half") {
                label {
                    `for`("interestStart")
                    +"Interest Start"
                }
                dateInput("interestStart", interestStartStore)
            }

            div("form-cell-half") {
                label {
                    `for`("paymentDay")
                    +"Payment Day"
                }
                numberInput("paymentDay", paymentDay.data)
            }
        }

        div("form-row") {
            div("form-cell-half") {
                label {
                    `for`("interestRate")
                    +"Interest Rate"
                }
                percentageInput("interestRate", interestRateStore)
            }

            div("form-cell-half") {
                label {
                    `for`("interestOnlyMonths")
                    +"Interest Only Months"
                }
                numberInput("interestOnlyMonths", interestOnlyMonths.data)
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
