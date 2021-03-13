package nl.dennisvanderzalm.parking.shared.network.repositories

import nl.dennisvanderzalm.parking.shared.core.models.ParkingReservation
import nl.dennisvanderzalm.parking.shared.core.repositories.GuestParkingRepository

class GuestParkingRepositoryImpl() : GuestParkingRepository {

    override suspend fun createParkingReservation(): ParkingReservation {
        return ParkingReservation("1")
    }
}