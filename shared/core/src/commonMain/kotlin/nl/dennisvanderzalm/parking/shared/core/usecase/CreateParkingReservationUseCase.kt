package nl.dennisvanderzalm.parking.shared.core.usecase

import nl.dennisvanderzalm.parking.shared.core.model.ParkingReservation
import nl.dennisvanderzalm.parking.shared.core.repository.GuestParkingRepository
import nl.dennisvanderzalm.parking.shared.core.usecase.base.CompletableUseCase
import nl.dennisvanderzalm.parking.shared.core.usecase.base.UseCase

class CreateParkingReservationUseCase(
    private val guestParkingRepository: GuestParkingRepository
) : CompletableUseCase<CreateParkingReservationUseCase.RequestValues> {

    override suspend fun get(requestValues: RequestValues) =
        guestParkingRepository.createParkingReservation(requestValues.reservation)

    data class RequestValues(val reservation: ParkingReservation) : UseCase.RequestValues
}
