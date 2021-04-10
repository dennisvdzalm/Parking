package nl.dennisvanderzalm.parking.shared.data.repository

import kotlinx.datetime.LocalDateTime
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
        from: LocalDateTime,
        until: LocalDateTime,
        licensePlate: DutchLicensePlateNumber,
        name: String
    ) = dataSource.createParkingSessions(from, until, licensePlate, name)


    override suspend fun endParkingReservation(reservationId: String) = dataSource.endParkingSessions(reservationId)

    override suspend fun getParkingHistory(): List<ParkingReservation> {
        val permits = storage.get<List<PermitsDataModel>>("permits")
        return permits.firstOrNull()?.let { permit ->
            permit.permitMedias.first().historyDataModel.reservations.items.map { reservationDataModel ->
                ParkingReservation(
                    reservationDataModel.reservationId,
                    reservationDataModel.validFrom,
                    reservationDataModel.validUntil,
                    reservationDataModel.licensePlate.value.let { if (it.isLicensePlate()) it.toLicensePlateNumber() else null },
                    reservationDataModel.units,
                    reservationDataModel.permitMediaCode
                )
            }
        } ?: emptyList()
    }
}
