package nl.dennisvanderzalm.parking.data.source

import kotlinx.datetime.*
import nl.dennisvanderzalm.parking.core.model.DutchLicensePlateNumber
import nl.dennisvanderzalm.parking.core.model.ParkingHistoryItem

interface GuestParkingDataSource {

    suspend fun createParkingSessions(
        from: Instant,
        until: Instant,
        licensePlate: DutchLicensePlateNumber,
        name: String
    )

    suspend fun endParkingSessions(reservationId: Int)

    suspend fun getParkingHistory(): List<ParkingHistoryItem>
}
