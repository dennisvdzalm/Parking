package nl.dennisvanderzalm.parking.shared.core.usecases

import kotlinx.datetime.LocalDateTime
import nl.dennisvanderzalm.parking.shared.core.models.DutchLicensePlateNumber
import nl.dennisvanderzalm.parking.shared.core.repositories.GuestParkingRepository
import nl.dennisvanderzalm.parking.shared.core.usecases.base.CompletableUseCase
import nl.dennisvanderzalm.parking.shared.core.usecases.base.UseCase

class CreateParkingReservationUseCase(private val guestParkingRepository: GuestParkingRepository) :
    CompletableUseCase<CreateParkingReservationUseCase.RequestValues> {

    override suspend fun get(requestValues: RequestValues) {
        return guestParkingRepository.createParkingReservation(
            requestValues.from,
            requestValues.until,
            requestValues.licensePlate,
            requestValues.name
        )
    }

    data class RequestValues(
        val from: LocalDateTime,
        val until: LocalDateTime,
        val licensePlate: DutchLicensePlateNumber,
        val name: String
    ) : UseCase.RequestValues
}
