package nl.dennisvanderzalm.parking.shared.core.usecases

import nl.dennisvanderzalm.parking.shared.core.repositories.GuestParkingRepository
import nl.dennisvanderzalm.parking.shared.core.usecases.base.CompletableUseCase
import nl.dennisvanderzalm.parking.shared.core.usecases.base.UseCase

class EndParkingReservationUseCase(private val guestParkingRepository: GuestParkingRepository) :
    CompletableUseCase<EndParkingReservationUseCase.RequestValues> {

    override suspend fun get(requestValues: RequestValues) =
        guestParkingRepository.endParkingReservation(requestValues.reservationId)


    data class RequestValues(val reservationId: String) : UseCase.RequestValues
}
