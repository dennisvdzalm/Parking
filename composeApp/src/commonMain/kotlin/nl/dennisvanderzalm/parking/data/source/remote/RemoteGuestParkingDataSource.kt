package nl.dennisvanderzalm.parking.data.source.remote

import kotlinx.datetime.Instant
import nl.dennisvanderzalm.parking.core.model.DutchLicensePlateNumber
import nl.dennisvanderzalm.parking.core.model.ParkingHistoryItem
import nl.dennisvanderzalm.parking.core.model.isLicensePlate
import nl.dennisvanderzalm.parking.core.model.toLicensePlateNumber
import nl.dennisvanderzalm.parking.data.service.GuestParkingService
import nl.dennisvanderzalm.parking.data.source.GuestParkingDataSource

class RemoteGuestParkingDataSource(
    private val parkingService: GuestParkingService
) : GuestParkingDataSource {

    override suspend fun createParkingSessions(
        from: Instant,
        until: Instant,
        licensePlate: DutchLicensePlateNumber,
        name: String
    ) {
        parkingService.createReservation(from, until, licensePlate.prettyNumber, name)
    }

    override suspend fun endParkingSessions(reservationId: Int) {
        parkingService.endParkingReservation(reservationId)
    }

    override suspend fun getParkingHistory(): List<ParkingHistoryItem> = parkingService.getParkingHistory().map {
        ParkingHistoryItem(
            it.reservationId,
            it.validFrom,
            it.validUntil,
            it.licensePlate.value.let { if (it.isLicensePlate()) it.toLicensePlateNumber() else null },
            it.units,
            it.permitMediaCode
        )
    }
}



