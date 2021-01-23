package com.github.cesure.mortgagecalc.model.serialization

import com.github.cesure.mortgagecalc.model.RepaymentPlan
import dev.fritz2.serialization.Serializer
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json

internal object RepaymentPlanSerializer : Serializer<RepaymentPlan, String> {

    override fun read(msg: String): RepaymentPlan =
        Json.decodeFromString(RepaymentPlan.serializer(), msg)

    override fun readList(msg: String): List<RepaymentPlan> =
        Json.decodeFromString(ListSerializer(RepaymentPlan.serializer()), msg)

    override fun write(item: RepaymentPlan): String =
        Json.encodeToString(RepaymentPlan.serializer(), item)

    override fun writeList(items: List<RepaymentPlan>): String =
        Json.encodeToString(ListSerializer(RepaymentPlan.serializer()), items)
}
