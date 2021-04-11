package nl.dennisvanderzalm.parking.shared.data.repository

import kotlinx.datetime.Instant
import nl.dennisvanderzalm.parking.shared.core.models.DutchLicensePlateNumber
import nl.dennisvanderzalm.parking.shared.core.models.ParkingReservation
import nl.dennisvanderzalm.parking.shared.core.models.isLicensePlate
import nl.dennisvanderzalm.parking.shared.core.models.toLicensePlateNumber
import nl.dennisvanderzalm.parking.shared.core.repositories.GuestParkingRepository
import nl.dennisvanderzalm.parking.shared.data.model.login.PermitsDataModel
import nl.dennisvanderzalm.parking.shared.data.source.GuestParkingDataSource
import nl.dennisvanderzalm.parking.shared.data.storage.LocalStorage

class GuestParkingRepositoryImpl(
    private val dataSource: GuestParkingDataSource,
    private val storage: LocalStorage
) : GuestParkingRepository {

    override suspend fun createParkingReservation(
        from: Instant,
        until: Instant,
        licensePlate: DutchLicensePlateNumber,
        name: String
    ) = dataSource.createParkingSessions(from, until, licensePlate, name)


    override suspend fun endParkingReservation(reservationId: String) = dataSource.endParkingSessions(reservationId)

    override suspend fun getParkingHistory(): List<ParkingReservation> = dataSource.getParkingHistory()
}
