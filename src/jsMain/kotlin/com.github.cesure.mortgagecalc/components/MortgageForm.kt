package com.github.cesure.mortgagecalc.components

import com.github.cesure.mortgagecalc.MortgageStore
import com.github.cesure.mortgagecalc.model.L
import dev.fritz2.dom.html.Div
import dev.fritz2.dom.html.RenderContext

fun RenderContext.mortgageForm(): Div {

    val amountStore = MortgageStore.sub(L.Mortgage.amount)
    val annuityStore = MortgageStore.sub(L.Mortgage.annuity)
    val interestStartStore = MortgageStore.sub(L.Mortgage.interestStart)
    val interestRateStore = MortgageStore.sub(L.Mortgage.interestRate)
    val paymentDayStore = MortgageStore.sub(L.Mortgage.paymentDay)
    val interestOnlyMonthsStore = MortgageStore.sub(L.Mortgage.interestOnlyMonths)

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
                numberInput("paymentDay", paymentDayStore) {
                    min("1")
                    max("31")
                }
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
                numberInput("interestOnlyMonths", interestOnlyMonthsStore) {
                    min("0")
                }
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
