package nl.dennisvanderzalm.parking.core.usecase

import nl.dennisvanderzalm.parking.core.model.ParkingReservation
import nl.dennisvanderzalm.parking.core.repository.GuestParkingRepository

class CreateParkingReservationUseCase(
    private val guestParkingRepository: GuestParkingRepository
){
    suspend operator fun invoke(reservation: ParkingReservation) {
        guestParkingRepository.createParkingReservation(reservation)
    }
}
