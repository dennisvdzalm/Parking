package nl.dennisvanderzalm.parking.shared.core.repository

import kotlinx.datetime.Instant
import nl.dennisvanderzalm.parking.shared.core.model.DutchLicensePlateNumber
import nl.dennisvanderzalm.parking.shared.core.model.ParkingHistoryItem
import nl.dennisvanderzalm.parking.shared.core.model.ParkingReservation

interface GuestParkingRepository {

    suspend fun createParkingReservation(reservation: ParkingReservation)

    suspend fun endParkingReservation(reservationId: Int)

    suspend fun getParkingHistory(): List<ParkingHistoryItem>

    fun resolveParkingReservations(
        respectPaidParkingHours: Boolean,
        start: Instant,
        end: Instant,
        licensePlate: DutchLicensePlateNumber,
        name: String
    ): List<ParkingReservation>
}
