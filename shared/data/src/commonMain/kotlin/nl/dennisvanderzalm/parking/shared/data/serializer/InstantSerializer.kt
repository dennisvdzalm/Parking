package nl.dennisvanderzalm.parking.shared.data.serializer

import kotlinx.datetime.*
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder


object InstantSerializer : KSerializer<Instant> {

    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("Instant", PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): Instant =
        decoder.decodeString()
            .toLocalDateTime()
            .toInstant(TimeZone.of("Europe/Amsterdam"))

    override fun serialize(encoder: Encoder, value: Instant) = encoder.encodeString(LocalDateTime.toString())
}
