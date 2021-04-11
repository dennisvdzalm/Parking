package nl.dennisvanderzalm.parking.shared.core.repositories

import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import nl.dennisvanderzalm.parking.shared.core.models.DutchLicensePlateNumber
import nl.dennisvanderzalm.parking.shared.core.models.ParkingReservation

interface GuestParkingRepository {

    suspend fun createParkingReservation(
        from: Instant,
        until: Instant,
        licensePlate: DutchLicensePlateNumber,
        name: String
    )

    suspend fun endParkingReservation(reservationId: Int)

    suspend fun getParkingHistory(): List<ParkingReservation>
}
