package nl.dennisvanderzalm.parking.shared.data.model.create

import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import nl.dennisvanderzalm.parking.shared.data.model.login.LicensePlateDataModel
import nl.dennisvanderzalm.parking.shared.data.serializer.InstantSerializer

@Serializable
data class CreateParkingReservationRequestDataModel(
    @SerialName("DateFrom")
    @Serializable(with = InstantSerializer::class)
    val DateFrom: Instant,
    @SerialName("DateUntil")
    @Serializable(with = InstantSerializer::class)
    val DateUntil: Instant,
    @SerialName("LicensePlate")
    val licensePlate: LicensePlateDataModel,
    @SerialName("permitMediaCode")
    val permitMediaCode: String,
    @SerialName("permitMediaTypeID")
    val permitMediaTypeId: Int
)
