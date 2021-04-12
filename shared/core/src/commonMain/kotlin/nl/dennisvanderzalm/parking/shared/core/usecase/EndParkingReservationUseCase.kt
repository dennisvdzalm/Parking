package nl.dennisvanderzalm.parking.shared.core.usecase

import nl.dennisvanderzalm.parking.shared.core.repository.GuestParkingRepository
import nl.dennisvanderzalm.parking.shared.core.usecase.base.CompletableUseCase
import nl.dennisvanderzalm.parking.shared.core.usecase.base.UseCase

class EndParkingReservationUseCase(private val guestParkingRepository: GuestParkingRepository) :
    CompletableUseCase<EndParkingReservationUseCase.RequestValues> {

    override suspend fun get(requestValues: RequestValues) =
        guestParkingRepository.endParkingReservation(requestValues.reservationId)


    data class RequestValues(val reservationId: Int) : UseCase.RequestValues
}
