package nl.dennisvanderzalm.parking.core.usecase

import nl.dennisvanderzalm.parking.core.repository.GuestParkingRepository

class EndParkingReservationUseCase(private val guestParkingRepository: GuestParkingRepository) {

    suspend operator fun invoke(reservationId: Int){
        guestParkingRepository.endParkingReservation(reservationId)
    }
}
