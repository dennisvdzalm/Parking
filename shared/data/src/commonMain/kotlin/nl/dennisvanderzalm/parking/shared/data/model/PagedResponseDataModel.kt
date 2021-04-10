package nl.dennisvanderzalm.parking.shared.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PagedResponseDataModel<T>(
    @SerialName("Items") val items: List<T>,
    @SerialName("Page") val page: Int,
    @SerialName("TotalPages") val totalPages: Int,
    @SerialName("TotalItems") val totalItems: Int
)
