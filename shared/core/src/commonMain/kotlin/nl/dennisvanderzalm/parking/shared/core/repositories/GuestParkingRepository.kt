package nl.dennisvanderzalm.parking.shared.core.repositories

import nl.dennisvanderzalm.parking.shared.core.models.ParkingReservation

interface GuestParkingRepository {

    suspend fun createParkingReservation(): ParkingReservation
}