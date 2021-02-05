package com.github.cesure.mortgagecalc.components

import com.github.cesure.mortgagecalc.model.L
import com.github.cesure.mortgagecalc.repositories.MortgageStore
import com.github.cesure.mortgagecalc.repositories.RepaymentPlanStore
import dev.fritz2.dom.html.Div
import dev.fritz2.dom.html.RenderContext

fun RenderContext.mortgageForm(): Div {

    val amountStore = MortgageStore.sub(L.Mortgage.totalAmount)
    val annuityStore = MortgageStore.sub(L.Mortgage.annuity)
    val interestRateStore = MortgageStore.sub(L.Mortgage.interestRate)
    val paymentDayStore = MortgageStore.sub(L.Mortgage.paymentDay)

    return div("container", "mortgage-form") {
        div("form-row") {
            div("form-cell-half") {
                label {
                    `for`("amount")
                    +"Amount"
                }
                div("input-decoration") {
                    currencyInput("amount", amountStore)
                }
            }

            div("form-cell-half") {
                label {
                    `for`("annuity")
                    +"Annuity"
                }
                div("input-decoration") {
                    currencyInput("annuity", annuityStore)
                }
            }
        }

        div("form-row") {
            div("form-cell-half") {
                label {
                    `for`("interestRate")
                    +"Interest Rate"
                }
                div("input-decoration") {
                    percentageInput("interestRate", interestRateStore)
                }
            }

            div("form-cell-half") {
                label {
                    `for`("paymentDay")
                    +"Payment Day"
                }
                div("input-decoration") {
                    numberInput("paymentDay", paymentDayStore) {
                        min("1")
                        max("31")
                    }
                }
            }
        }

        div("form-row") {
            div("form-cell-full") {
                transactionInput()
            }
        }

        div("form-row") {
            div("form-cell-full") {
                button {
                    +"Calculate"
                    clicks.map { MortgageStore.data.value } handledBy RepaymentPlanStore.calculateRepaymentPlan
                }
            }
        }
    }
}
