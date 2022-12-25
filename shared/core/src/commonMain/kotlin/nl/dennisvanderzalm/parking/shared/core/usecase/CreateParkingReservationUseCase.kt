package nl.dennisvanderzalm.parking.shared.core.usecase

import nl.dennisvanderzalm.parking.shared.core.model.ParkingReservation
import nl.dennisvanderzalm.parking.shared.core.repository.GuestParkingRepository
class CreateParkingReservationUseCase(
    private val guestParkingRepository: GuestParkingRepository
){
    suspend operator fun invoke(reservation: ParkingReservation) {
        guestParkingRepository.createParkingReservation(reservation)
    }
}
