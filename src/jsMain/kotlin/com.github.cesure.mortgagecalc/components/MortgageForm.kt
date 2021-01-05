package com.github.cesure.mortgagecalc.components

import com.github.cesure.mortgagecalc.MortgageStore
import com.github.cesure.mortgagecalc.model.Formats
import com.github.cesure.mortgagecalc.model.L
import dev.fritz2.dom.html.Div
import dev.fritz2.dom.html.RenderContext

fun RenderContext.mortgageForm(): Div {

    val amount = MortgageStore.sub(L.Mortgage.amount + Formats.currency)
    val annuity = MortgageStore.sub(L.Mortgage.annuity + Formats.currency)
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

                currencyInput(id = "amount", value = amount.data)
            }

            div("form-cell-half") {
                label {
                    `for`("annuity")
                    +"Annuity"
                }
                currencyInput(id = "annuity", value = annuity.data)
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
                numberInput(id = "paymentDay", value = paymentDay.data)
            }
        }

        div("form-row") {
            div("form-cell-half") {
                label {
                    `for`("interestRate")
                    +"Interest Rate"
                }
                percentageInput(id = "interestRate", value = interestRate.data)
            }

            div("form-cell-half") {
                label {
                    `for`("interestOnlyMonths")
                    +"Interest Only Months"
                }
                numberInput(id = "interestOnlyMonths", value = interestOnlyMonths.data)
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
