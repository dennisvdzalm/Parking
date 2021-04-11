package nl.dennisvanderzalm.parking.shared.data.source.remote

import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import nl.dennisvanderzalm.parking.shared.core.models.DutchLicensePlateNumber
import nl.dennisvanderzalm.parking.shared.core.models.ParkingReservation
import nl.dennisvanderzalm.parking.shared.core.models.isLicensePlate
import nl.dennisvanderzalm.parking.shared.core.models.toLicensePlateNumber
import nl.dennisvanderzalm.parking.shared.data.service.GuestParkingService
import nl.dennisvanderzalm.parking.shared.data.source.GuestParkingDataSource
import nl.dennisvanderzalm.parking.shared.data.storage.LocalStorage

class RemoteGuestParkingDataSource(
    private val parkingService: GuestParkingService,
    private val localStorage: LocalStorage
) : GuestParkingDataSource {

    override suspend fun createParkingSessions(
        from: Instant,
        until: Instant,
        licensePlate: DutchLicensePlateNumber,
        name: String
    ) = parkingService.createReservation(
        from,
        until,
        licensePlate.prettyNumber,
        name,
        localStorage.get("permitMediaCode"),
        localStorage.get("permitMediaTypeId")
    )


    override suspend fun endParkingSessions(reservationId: String) = parkingService.endParkingReservation(
        reservationId,
        localStorage.get("permitMediaCode"),
        localStorage.get("permitMediaTypeId")
    )

    override suspend fun getParkingHistory(): List<ParkingReservation> = parkingService.getParkingHistory().map {
        ParkingReservation(
            it.reservationId,
            it.validFrom,
            it.validUntil,
            it.licensePlate.value.let { if (it.isLicensePlate()) it.toLicensePlateNumber() else null },
            it.units,
            it.permitMediaCode
        )
    }
}

