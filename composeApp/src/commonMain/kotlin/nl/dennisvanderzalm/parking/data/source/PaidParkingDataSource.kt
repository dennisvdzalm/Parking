package nl.dennisvanderzalm.parking.data.source

import kotlinx.datetime.LocalDate
import nl.dennisvanderzalm.parking.core.model.ParkingZone
import nl.dennisvanderzalm.parking.data.model.PaidParkingTimes

interface PaidParkingDataSource {

    fun getPaidParkingHours(zone: ParkingZone, localDate: LocalDate): PaidParkingTimes?
}
