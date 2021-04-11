package nl.dennisvanderzalm.parking.shared.data.repository

import kotlinx.datetime.Instant
import nl.dennisvanderzalm.parking.shared.core.models.DutchLicensePlateNumber
import nl.dennisvanderzalm.parking.shared.core.models.ParkingReservation
import nl.dennisvanderzalm.parking.shared.core.repositories.GuestParkingRepository
import nl.dennisvanderzalm.parking.shared.data.source.GuestParkingDataSource

class GuestParkingRepositoryImpl(
    private val dataSource: GuestParkingDataSource
) : GuestParkingRepository {

    override suspend fun createParkingReservation(
        from: Instant,
        until: Instant,
        licensePlate: DutchLicensePlateNumber,
        name: String
    ) = dataSource.createParkingSessions(from, until, licensePlate, name)


    override suspend fun endParkingReservation(reservationId: Int) = dataSource.endParkingSessions(reservationId)

    override suspend fun getParkingHistory(): List<ParkingReservation> = dataSource.getParkingHistory()
}
