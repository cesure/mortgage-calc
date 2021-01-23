package com.github.cesure.mortgagecalc.model.serialization

import com.github.cesure.mortgagecalc.model.RepaymentPlan
import dev.fritz2.serialization.Serializer
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

internal object RepaymentPlanSerializer : Serializer<RepaymentPlan, String> {

    override fun read(msg: String): RepaymentPlan = Json.decodeFromString(msg)

    override fun readList(msg: String): List<RepaymentPlan> = Json.decodeFromString(msg)

    override fun write(item: RepaymentPlan): String = Json.encodeToString(item)

    override fun writeList(items: List<RepaymentPlan>): String = Json.encodeToString(items)
}
