package nl.dennisvanderzalm.parking.core.repository

import kotlinx.datetime.Instant
import nl.dennisvanderzalm.parking.core.model.DutchLicensePlateNumber
import nl.dennisvanderzalm.parking.core.model.ParkingHistoryItem
import nl.dennisvanderzalm.parking.core.model.ParkingReservation
import nl.dennisvanderzalm.parking.core.model.ParkingZone

interface GuestParkingRepository {

    suspend fun createParkingReservation(reservation: ParkingReservation)

    suspend fun endParkingReservation(reservationId: Int)

    suspend fun getParkingHistory(): List<ParkingHistoryItem>

    fun resolveParkingReservations(
        respectPaidParkingHours: Boolean,
        start: Instant,
        end: Instant,
        licensePlate: DutchLicensePlateNumber,
        name: String,
        zone: ParkingZone
    ): List<ParkingReservation>
}
