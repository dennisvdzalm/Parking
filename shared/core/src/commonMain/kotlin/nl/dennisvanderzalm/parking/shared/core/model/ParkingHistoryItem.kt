package nl.dennisvanderzalm.parking.shared.core.model

import kotlinx.datetime.Instant

data class ParkingHistoryItem(
    val reservationId: Int,
    val validFrom: Instant,
    val validUntil: Instant,
    val licensePlate: DutchLicensePlateNumber?,
    val units: Int,
    val permitMediaCode: String
)
