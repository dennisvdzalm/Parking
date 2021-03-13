package nl.dennisvanderzalm.parking.shared.network.models.login

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginResponseDataModel(
    @SerialName("Token") val token: String,
    @SerialName("Name") val name: String,
    @SerialName("Permits") val permits: List<PermitsDataModel>
)