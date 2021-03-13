package nl.dennisvanderzalm.parking.shared.network.models.login

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PermitMediaDataModel(
     @SerialName("TypeID") val typeId: Int,
     @SerialName("Code") val code: String,
     @SerialName("ZoneCode") val zoneCode: String,
     @SerialName("UnitFormat") val unitFormat: Int,
     @SerialName("UnitPrice") val unitPrice: Double,
     @SerialName("UpgradeUnits") val upgradeUnits: List<Int>,
     @SerialName("Balance") val balance: Int,
     @SerialName("StartTariff") val startTariff: Double,
     @SerialName("ActiveReservations") val activeReservations: List<ReservationItemDataModel>,
     @SerialName("LicensePlates") val licensePlates: List<LicensePlateDataModel>,
     @SerialName("History") val historyDataModel: HistoryDataModel,
     @SerialName("RemainingUpgrades") val remainingUpgrades: Int,
     @SerialName("RemainingDowngrades") val remainingDowngrades: Int?,
     @SerialName("BalanceLimit") val balanceLimit: Int
)