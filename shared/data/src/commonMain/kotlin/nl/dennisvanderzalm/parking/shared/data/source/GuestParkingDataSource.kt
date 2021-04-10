package nl.dennisvanderzalm.parking.shared.data.source

import kotlinx.datetime.*
import nl.dennisvanderzalm.parking.shared.core.models.DutchLicensePlateNumber

interface GuestParkingDataSource {

    suspend fun createParkingSessions(
        from: LocalDateTime,
        until: LocalDateTime,
        licensePlate: DutchLicensePlateNumber,
        name: String
    )

    suspend fun endParkingSessions(reservationId: String)
}
