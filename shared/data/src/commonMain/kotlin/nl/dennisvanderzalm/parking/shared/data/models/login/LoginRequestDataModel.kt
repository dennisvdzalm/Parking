package nl.dennisvanderzalm.parking.shared.data.models.login

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginRequestDataModel(
    @SerialName("identifier")
    val identifier: String,
    @SerialName("loginMethod")
    val loginMethod: String = "Pas",
    @SerialName("password")
    val password: String,
    @SerialName("permitMediaTypeID")
    val permitMediaTypeId: Int = 1,
    @SerialName("resetCode")
    val resetCode: String? = null,
    @SerialName("zipCode")
    val zipCode: String? = null
)