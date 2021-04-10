package nl.dennisvanderzalm.parking.shared.core.repositories

import kotlinx.datetime.LocalDateTime
import nl.dennisvanderzalm.parking.shared.core.models.DutchLicensePlateNumber
import nl.dennisvanderzalm.parking.shared.core.models.ParkingReservation

interface GuestParkingRepository {

    suspend fun createParkingReservation(
        from: LocalDateTime,
        until: LocalDateTime,
        licensePlate: DutchLicensePlateNumber,
        name: String
    )

    suspend fun endParkingReservation(reservationId: String)

    suspend fun getParkingHistory(): List<ParkingReservation>
}
