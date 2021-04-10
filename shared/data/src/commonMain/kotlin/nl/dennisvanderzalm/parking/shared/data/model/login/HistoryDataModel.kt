package nl.dennisvanderzalm.parking.shared.data.model.login

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import nl.dennisvanderzalm.parking.shared.data.model.PagedResponseDataModel

@Serializable
data class HistoryDataModel(
    @SerialName("Reservations") val reservations: PagedResponseDataModel<ReservationItemDataModel>,
    @SerialName("Upgrades") val upgrades: PagedResponseDataModel<UpgradeDataModel>
)
