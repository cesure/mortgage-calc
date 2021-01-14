package com.github.cesure.mortgagecalc.components

import dev.fritz2.dom.html.Div
import dev.fritz2.dom.html.RenderContext

fun RenderContext.repaymentPlanList(): Div {
    return div("container", "repayment-plan-list") {
        +"Repayment Plan List"
    }
}
