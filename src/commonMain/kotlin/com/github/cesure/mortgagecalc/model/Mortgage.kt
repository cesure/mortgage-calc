package com.github.cesure.mortgagecalc.model

import com.github.cesure.mortgagecalc.model.serialization.DecimalSerializer
import com.github.cesure.mortgagecalc.model.serialization.LocalDateSerializer
import dev.fritz2.lenses.Lenses
import dev.fritz2.serialization.Serializer
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json

@Lenses
@Serializable
data class Mortgage(
    @Serializable(DecimalSerializer::class)
    val amount: Decimal = Decimal(intVal = 100_000),
    @Serializable(DecimalSerializer::class)
    val annuity: Decimal = Decimal(intVal = 1_000),
    @Serializable(LocalDateSerializer::class)
    val interestStart: LocalDate = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date,
    val paymentDay: Int = 31,
    @Serializable(DecimalSerializer::class)
    val interestRate: Decimal = Decimal("0.0175"),
    val interestOnlyMonths: Int = 0,
)

object MortgageSerializer : Serializer<Mortgage, String> {

    override fun read(msg: String): Mortgage =
        Json.decodeFromString(Mortgage.serializer(), msg)

    override fun readList(msg: String): List<Mortgage> =
        Json.decodeFromString(ListSerializer(Mortgage.serializer()), msg)

    override fun write(item: Mortgage): String =
        Json.encodeToString(Mortgage.serializer(), item)

    override fun writeList(items: List<Mortgage>): String =
        Json.encodeToString(ListSerializer(Mortgage.serializer()), items)
}
