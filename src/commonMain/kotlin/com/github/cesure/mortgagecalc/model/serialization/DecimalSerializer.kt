package com.github.cesure.mortgagecalc.model.serialization

import com.github.cesure.mortgagecalc.model.Decimal
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind.STRING
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

internal object DecimalSerializer : KSerializer<Decimal> {

    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("Decimal", STRING)

    override fun serialize(encoder: Encoder, value: Decimal) = encoder.encodeString(value.toString())

    override fun deserialize(decoder: Decoder): Decimal = Decimal(decoder.decodeString())
}
