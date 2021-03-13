package nl.dennisvanderzalm.parking.shared.network.models.login

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LicensePlateDataModel(
    @SerialName("Value") val value: String,
    @SerialName("Name") val name: String?
)