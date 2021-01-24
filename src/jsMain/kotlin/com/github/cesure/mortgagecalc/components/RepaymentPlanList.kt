package com.github.cesure.mortgagecalc.components

import com.github.cesure.mortgagecalc.model.RepaymentPlan
import com.github.cesure.mortgagecalc.model.formatCurrency
import dev.fritz2.dom.html.Div
import dev.fritz2.dom.html.RenderContext

fun RenderContext.repaymentPlanList(repaymentPlan: RepaymentPlan): Div = div("container", "repayment-plan-list") {
    ul {
        li {
            +repaymentPlan.first().transaction.amount.formatCurrency(hideSign = true)
        }
    }
}
