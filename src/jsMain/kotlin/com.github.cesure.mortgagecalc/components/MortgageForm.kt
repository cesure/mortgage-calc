package com.github.cesure.mortgagecalc.components

import com.github.cesure.mortgagecalc.MortgageStore
import com.github.cesure.mortgagecalc.model.Formats
import com.github.cesure.mortgagecalc.model.L
import dev.fritz2.dom.html.Div
import dev.fritz2.dom.html.RenderContext

fun RenderContext.mortgageForm(): Div {

    val amountCurrency = MortgageStore.sub(L.Mortgage.amount + Formats.currency)
    val amountDecimal = MortgageStore.sub(L.Mortgage.amount + Formats.decimal)
    val annuityCurrency = MortgageStore.sub(L.Mortgage.annuity + Formats.currency)
    val annuityDecimal = MortgageStore.sub(L.Mortgage.annuity + Formats.decimal)
    val interestStart = MortgageStore.sub(L.Mortgage.interestStart + Formats.localDate)
    val interestRate = MortgageStore.sub(L.Mortgage.interestRate + Formats.percentage)
    val paymentDay = MortgageStore.sub(L.Mortgage.paymentDay + Formats.integer)
    val interestOnlyMonths = MortgageStore.sub(L.Mortgage.interestOnlyMonths + Formats.integer)

    return div("container", "mortgage-form") {
        div("form-row") {
            div("form-cell-half") {
                label {
                    `for`("amount")
                    +"Amount"
                }

                formattedInput("amount", amountCurrency, amountDecimal)
//                input { value(MortgageStore.data.asString()) }
            }

            div("form-cell-half") {
                label {
                    `for`("annuity")
                    +"Annuity"
                }
                formattedInput("annuity", annuityCurrency, annuityDecimal)
            }
        }

        div("form-row") {
            div("form-cell-half") {
                label {
                    `for`("interestStart")
                    +"Interest Start"
                }
                dateInput("interestStart", interestStart.data)
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
                percentageInput("interestRate", interestRate.data)
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
