package nl.dennisvanderzalm.parking.shared.network.models.login

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import nl.dennisvanderzalm.parking.shared.network.models.PagedResponseDataModel

@Serializable
data class HistoryDataModel(
    @SerialName("Reservations") val reservations: PagedResponseDataModel<ReservationItemDataModel>,
    @SerialName("Upgrades") val upgrades: PagedResponseDataModel<UpgradeDataModel>
)