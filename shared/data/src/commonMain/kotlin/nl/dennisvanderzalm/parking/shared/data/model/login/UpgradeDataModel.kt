package nl.dennisvanderzalm.parking.shared.data.model.login

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UpgradeDataModel(
    @SerialName("ValidFrom") val validFrom: String,
    @SerialName("Units") val units: Int,
    @SerialName("Amount") val amount: Double,
    @SerialName("PermitMediaCode") val permitMediaCode: String
)
