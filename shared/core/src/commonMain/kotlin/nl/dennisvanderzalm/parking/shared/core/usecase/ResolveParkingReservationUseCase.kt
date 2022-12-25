package nl.dennisvanderzalm.parking.shared.core.usecase

import kotlinx.datetime.Instant
import nl.dennisvanderzalm.parking.shared.core.model.DutchLicensePlateNumber
import nl.dennisvanderzalm.parking.shared.core.model.ParkingReservation
import nl.dennisvanderzalm.parking.shared.core.model.ParkingZone
import nl.dennisvanderzalm.parking.shared.core.repository.GuestParkingRepository

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
