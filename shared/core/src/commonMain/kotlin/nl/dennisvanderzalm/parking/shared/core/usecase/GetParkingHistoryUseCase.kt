package nl.dennisvanderzalm.parking.shared.core.usecase

import nl.dennisvanderzalm.parking.shared.core.model.ParkingHistoryItem
import nl.dennisvanderzalm.parking.shared.core.repository.GuestParkingRepository

class GetParkingHistoryUseCase(private val guestParkingRepository: GuestParkingRepository) {
    suspend operator fun invoke(): List<ParkingHistoryItem> = guestParkingRepository.getParkingHistory()
}
