package nl.dennisvanderzalm.parking.shared.core.usecase

import kotlinx.datetime.Instant
import nl.dennisvanderzalm.parking.shared.core.model.DutchLicensePlateNumber
import nl.dennisvanderzalm.parking.shared.core.model.ParkingReservation
import nl.dennisvanderzalm.parking.shared.core.repository.GuestParkingRepository
import nl.dennisvanderzalm.parking.shared.core.usecase.base.UseCase

class ResolveParkingReservationUseCase(private val parkingRepository: GuestParkingRepository) :
    UseCase<ResolveParkingReservationUseCase.RequestValues, List<ParkingReservation>> {

    override suspend fun get(requestValues: RequestValues): List<ParkingReservation> =
        parkingRepository.resolveParkingReservations(
            requestValues.start,
            requestValues.end,
            requestValues.licensePlateNumber,
            requestValues.name
        )

    data class RequestValues(
        val start: Instant,
        val end: Instant,
        val licensePlateNumber: DutchLicensePlateNumber,
        val name: String
    ) : UseCase.RequestValues
}
