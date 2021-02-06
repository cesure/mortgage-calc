package com.github.cesure.mortgagecalc

import com.github.cesure.mortgagecalc.components.mortgageForm
import com.github.cesure.mortgagecalc.components.repaymentPlanList
import com.github.cesure.mortgagecalc.repositories.MortgageStore
import com.github.cesure.mortgagecalc.repositories.RepaymentPlanStore
import dev.fritz2.binding.invoke
import dev.fritz2.dom.html.render
import dev.fritz2.dom.mount

fun main() {
    MortgageStore.load.invoke()

    js("require('styles.css');")
    render {
        div(id = "mortgage-calc-app") {
            h1 {
                +"Mortgage Calc"
            }

            mortgageForm()

            MortgageStore.data.renderElement {
                div("container", "repayment-plan-graph") {
                    +it.toString()
                }
            }

            RepaymentPlanStore.data.render { repaymentPlan ->
                repaymentPlan?.let { repaymentPlanList(it) }
            }
        }
    }.mount("target")
}
