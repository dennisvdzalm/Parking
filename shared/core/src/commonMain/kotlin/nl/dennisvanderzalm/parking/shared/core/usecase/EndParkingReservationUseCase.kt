package nl.dennisvanderzalm.parking.shared.core.usecase

import nl.dennisvanderzalm.parking.shared.core.repository.GuestParkingRepository

class EndParkingReservationUseCase(private val guestParkingRepository: GuestParkingRepository) {

    suspend operator fun invoke(reservationId: Int){
        guestParkingRepository.endParkingReservation(reservationId)
    }
}
