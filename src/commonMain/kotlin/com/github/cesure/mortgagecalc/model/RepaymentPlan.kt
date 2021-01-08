package com.github.cesure.mortgagecalc.model

import kotlinx.serialization.Serializable

@Serializable
data class RepaymentPlan(
    val repayments: List<String> = emptyList()
)
