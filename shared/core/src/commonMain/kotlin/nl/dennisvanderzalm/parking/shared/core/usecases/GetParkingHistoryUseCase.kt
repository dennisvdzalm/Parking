package nl.dennisvanderzalm.parking.shared.core.usecases

import nl.dennisvanderzalm.parking.shared.core.models.ParkingReservation
import nl.dennisvanderzalm.parking.shared.core.repositories.GuestParkingRepository
import nl.dennisvanderzalm.parking.shared.core.usecases.base.UseCase

class GetParkingHistoryUseCase(private val guestParkingRepository: GuestParkingRepository) :
    UseCase<GetParkingHistoryUseCase.RequestValues, List<ParkingReservation>> {

    override suspend fun get(requestValues: RequestValues): List<ParkingReservation> =
        guestParkingRepository.getParkingHistory()

    object RequestValues : UseCase.RequestValues
}