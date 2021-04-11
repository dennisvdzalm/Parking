package nl.dennisvanderzalm.parking.shared.data.model.create

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EndParkingReservationRequestDataModel(
    @SerialName("ReservationID")
    val reservationId: Int,
    @SerialName("permitMediaCode")
    val permitMediaCode: String,
    @SerialName("permitMediaTypeId")
    val permitMediaTypeId: Int
)
