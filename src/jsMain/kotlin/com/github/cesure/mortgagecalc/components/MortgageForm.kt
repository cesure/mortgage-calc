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
                currencyInput("amount", amountStore)
            }
            div("form-cell-half") {
                percentageInput("interestRate", interestRateStore)
            }
        }

        div("form-row") {
            div("form-cell-half") {
                currencyInput("annuity", annuityStore)
            }
            div("form-cell-half") {
                numberInput("paymentDay", paymentDayStore) {
                    min("1")
                    max("31")
                }
            }
        }

        div("form-row") {
            div("form-cell-full") {
                transactionInput("payouts")
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
