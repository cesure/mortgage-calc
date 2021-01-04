package com.github.cesure.mortgagecalc.components

import dev.fritz2.dom.html.Div
import dev.fritz2.dom.html.RenderContext

fun RenderContext.repaymentPlanGraph(): Div {
    return div("container", "repayment-plan-graph") {
        +"Repayment Plan Graph"
    }
}
