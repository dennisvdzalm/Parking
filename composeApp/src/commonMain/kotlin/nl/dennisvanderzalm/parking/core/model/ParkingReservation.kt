package nl.dennisvanderzalm.parking.core.model

import kotlinx.datetime.Instant
import nl.dennisvanderzalm.parking.core.model.DutchLicensePlateNumber

data class ParkingReservation(
    val start: Instant,
    val end: Instant,
    val licensePlate: DutchLicensePlateNumber,
    val name: String
)
