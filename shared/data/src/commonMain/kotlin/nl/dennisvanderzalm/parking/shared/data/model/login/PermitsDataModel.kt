package nl.dennisvanderzalm.parking.shared.data.model.login

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PermitsDataModel(
    @SerialName("Code") val code: String?,
    @SerialName("Type") val type: String?,
    @SerialName("TypeCode") val typeCode: String?,
    @SerialName("PermitMedias") val permitMedias: List<PermitMediaDataModel>
)
