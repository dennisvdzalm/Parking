package nl.dennisvanderzalm.parking.shared.data.model.login

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PermitMediaDataModel(
     @SerialName("TypeID") val typeId: Int,
     @SerialName("Code") val code: String,
     @SerialName("ZoneCode") val zoneCode: String? = null,
     @SerialName("UnitFormat") val unitFormat: Int? = null,
     @SerialName("UnitPrice") val unitPrice: Double? = null,
     @SerialName("UpgradeUnits") val upgradeUnits: List<Int?> = emptyList(),
     @SerialName("Balance") val balance: Int,
     @SerialName("StartTariff") val startTariff: Double? = null,
     @SerialName("ActiveReservations") val activeReservations: List<ReservationItemDataModel>,
     @SerialName("LicensePlates") val licensePlates: List<LicensePlateDataModel>,
     @SerialName("History") val historyDataModel: HistoryDataModel,
     @SerialName("RemainingUpgrades") val remainingUpgrades: Int,
     @SerialName("RemainingDowngrades") val remainingDowngrades: Int? = null,
     @SerialName("BalanceLimit") val balanceLimit: Int? = null
)
