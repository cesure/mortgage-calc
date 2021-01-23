package com.github.cesure.mortgagecalc.components

import com.github.cesure.mortgagecalc.model.formatCurrency
import com.github.cesure.mortgagecalc.repositories.RepaymentPlanStore
import dev.fritz2.dom.html.Div
import dev.fritz2.dom.html.RenderContext

fun RenderContext.repaymentPlanList(): Div = div("container", "repayment-plan-list") {
    RepaymentPlanStore.data.renderElement {
        ul {
            li {
                +"${it?.first()?.amount?.formatCurrency()}"
            }
        }
    }
}
