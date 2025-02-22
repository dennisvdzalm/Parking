package nl.dennisvanderzalm.parking.core.usecase

import nl.dennisvanderzalm.parking.core.model.ParkingHistoryItem
import nl.dennisvanderzalm.parking.core.repository.GuestParkingRepository

class GetParkingHistoryUseCase(private val guestParkingRepository: GuestParkingRepository) {
    suspend operator fun invoke(): List<ParkingHistoryItem> = guestParkingRepository.getParkingHistory()
}
