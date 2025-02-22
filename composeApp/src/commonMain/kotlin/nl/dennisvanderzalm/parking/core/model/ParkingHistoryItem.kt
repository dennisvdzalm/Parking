package nl.dennisvanderzalm.parking.core.model

import kotlinx.datetime.Instant
import nl.dennisvanderzalm.parking.core.model.DutchLicensePlateNumber

data class ParkingHistoryItem(
    val reservationId: Int,
    val validFrom: Instant,
    val validUntil: Instant,
    val licensePlate: DutchLicensePlateNumber?,
    val units: Int,
    val permitMediaCode: String
)
