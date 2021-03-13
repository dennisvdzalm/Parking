package nl.dennisvanderzalm.parking.shared.core.usecases

import nl.dennisvanderzalm.parking.shared.core.models.ParkingReservation
import nl.dennisvanderzalm.parking.shared.core.repositories.GuestParkingRepository
import nl.dennisvanderzalm.parking.shared.core.usecases.base.UseCase

class CreateParkingSessionUseCase(private val guestParkingRepository: GuestParkingRepository) :
    UseCase<CreateParkingSessionUseCase.RequestValues, ParkingReservation> {

    override suspend fun get(requestValues: RequestValues): ParkingReservation {
        return guestParkingRepository.createParkingReservation()
    }

    object RequestValues : UseCase.RequestValues
}