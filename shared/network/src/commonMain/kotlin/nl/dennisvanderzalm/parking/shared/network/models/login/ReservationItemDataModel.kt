package nl.dennisvanderzalm.parking.shared.network.models.login

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ReservationItemDataModel(
    @SerialName("ReservationID") val reservationId: Int,
    @SerialName("ValidFrom") val validFrom: String,
    @SerialName("ValidUntil") val validUntil: String,
    @SerialName("LicensePlate") val licensePlate: LicensePlateDataModel,
    @SerialName("Units") val units: Int,
    @SerialName("PermitMediaCode") val permitMediaCode: String
)