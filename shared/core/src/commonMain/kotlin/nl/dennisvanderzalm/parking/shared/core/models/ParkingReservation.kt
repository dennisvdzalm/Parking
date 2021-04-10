package nl.dennisvanderzalm.parking.shared.core.models

import kotlinx.datetime.Instant

data class ParkingReservation(
    val reservationId: Int,
    val validFrom: Instant,
    val validUntil: Instant,
    val licensePlate: DutchLicensePlateNumber?,
    val units: Int,
    val permitMediaCode: String
)
