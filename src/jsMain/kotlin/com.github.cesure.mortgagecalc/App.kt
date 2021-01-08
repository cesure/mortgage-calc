package com.github.cesure.mortgagecalc

import com.github.cesure.mortgagecalc.components.mortgageForm
import dev.fritz2.dom.html.render
import dev.fritz2.dom.mount

fun main() {
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

//            repaymentPlanGraph()
//            repaymentPlanList()
        }
    }.mount("target")
}
