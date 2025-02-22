package nl.dennisvanderzalm.parking.core.usecase

import kotlinx.datetime.Instant
import nl.dennisvanderzalm.parking.core.model.DutchLicensePlateNumber
import nl.dennisvanderzalm.parking.core.model.ParkingReservation
import nl.dennisvanderzalm.parking.core.model.ParkingZone
import nl.dennisvanderzalm.parking.core.repository.GuestParkingRepository

class ResolveParkingReservationUseCase(private val parkingRepository: GuestParkingRepository) {
    operator fun invoke(
        respectPaidParkingHours: Boolean,
        start: Instant,
        end: Instant,
        licensePlateNumber: DutchLicensePlateNumber,
        name: String,
        zone: ParkingZone
    ): List<ParkingReservation> =
        parkingRepository.resolveParkingReservations(
            respectPaidParkingHours = respectPaidParkingHours,
            start = start,
            end = end,
            licensePlate = licensePlateNumber,
            name = name,
            zone = zone
        )
}
