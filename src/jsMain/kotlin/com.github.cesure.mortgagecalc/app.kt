package com.github.cesure.mortgagecalc

import com.github.cesure.mortgagecalc.components.mortgageForm
import com.github.cesure.mortgagecalc.repositories.mortgageStore
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

            mortgageStore.data.renderElement {
                div("container", "repayment-plan-graph") {
                    +it.toString()
                }
            }

//            repaymentPlanGraph()
//            repaymentPlanList()
        }
    }.mount("target")
}
