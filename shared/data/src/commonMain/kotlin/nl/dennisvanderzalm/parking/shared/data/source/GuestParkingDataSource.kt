package nl.dennisvanderzalm.parking.shared.data.source

import kotlinx.datetime.*
import nl.dennisvanderzalm.parking.shared.core.models.DutchLicensePlateNumber
import nl.dennisvanderzalm.parking.shared.core.models.ParkingReservation

interface GuestParkingDataSource {

    suspend fun createParkingSessions(
        from: Instant,
        until: Instant,
        licensePlate: DutchLicensePlateNumber,
        name: String
    )

    suspend fun endParkingSessions(reservationId: String)

    suspend fun getParkingHistory(): List<ParkingReservation>
}
