package nl.dennisvanderzalm.parking.shared.data.model.login

import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import nl.dennisvanderzalm.parking.shared.data.serializer.InstantSerializer

@Serializable
data class ReservationItemDataModel(
    @SerialName("ReservationID") val reservationId: Int,
    @Serializable(with = InstantSerializer::class) @SerialName("ValidFrom") val validFrom: Instant,
    @Serializable(with = InstantSerializer::class) @SerialName("ValidUntil") val validUntil: Instant,
    @SerialName("LicensePlate") val licensePlate: LicensePlateDataModel,
    @SerialName("Units") val units: Int,
    @SerialName("PermitMediaCode") val permitMediaCode: String
)
