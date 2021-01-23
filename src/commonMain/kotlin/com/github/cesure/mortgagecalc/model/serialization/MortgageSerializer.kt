package com.github.cesure.mortgagecalc.model.serialization

import com.github.cesure.mortgagecalc.model.Mortgage
import dev.fritz2.serialization.Serializer
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json

internal object MortgageSerializer : Serializer<Mortgage, String> {

    override fun read(msg: String): Mortgage =
        Json.decodeFromString(Mortgage.serializer(), msg)

    override fun readList(msg: String): List<Mortgage> =
        Json.decodeFromString(ListSerializer(Mortgage.serializer()), msg)

    override fun write(item: Mortgage): String =
        Json.encodeToString(Mortgage.serializer(), item)

    override fun writeList(items: List<Mortgage>): String =
        Json.encodeToString(ListSerializer(Mortgage.serializer()), items)
}
