package nl.dennisvanderzalm.parking.shared.core.usecase

import nl.dennisvanderzalm.parking.shared.core.model.ParkingHistoryItem
import nl.dennisvanderzalm.parking.shared.core.repository.GuestParkingRepository
import nl.dennisvanderzalm.parking.shared.core.usecase.base.UseCase

class GetParkingHistoryUseCase(private val guestParkingRepository: GuestParkingRepository) :
    UseCase<GetParkingHistoryUseCase.RequestValues, List<ParkingHistoryItem>> {

    override suspend fun get(requestValues: RequestValues): List<ParkingHistoryItem> =
        guestParkingRepository.getParkingHistory()

    object RequestValues : UseCase.RequestValues
}
