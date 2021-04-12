package nl.dennisvanderzalm.parking.shared.data.source

import kotlinx.datetime.*
import nl.dennisvanderzalm.parking.shared.core.model.DutchLicensePlateNumber
import nl.dennisvanderzalm.parking.shared.core.model.ParkingHistoryItem

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
