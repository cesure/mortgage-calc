package com.github.cesure.mortgagecalc

import com.github.cesure.mortgagecalc.components.mortgageForm
import com.github.cesure.mortgagecalc.components.repaymentPlanList
import com.github.cesure.mortgagecalc.model.Decimal
import com.github.cesure.mortgagecalc.repositories.MortgageStore
import dev.fritz2.binding.invoke
import dev.fritz2.dom.html.render
import dev.fritz2.dom.mount

fun main() {
    val foo = Decimal("1.0")
    console.log(foo)
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

//            repaymentPlanGraph()
            repaymentPlanList()
        }
    }.mount("target")
}
