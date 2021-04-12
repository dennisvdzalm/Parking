package nl.dennisvanderzalm.parking.shared.core.model

import kotlinx.datetime.Instant

data class ParkingReservation(
    val start: Instant,
    val end: Instant,
    val licensePlate: DutchLicensePlateNumber,
    val name: String
)
