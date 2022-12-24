package nl.dennisvanderzalm.parking.shared.data.source

import kotlinx.datetime.LocalDate
import nl.dennisvanderzalm.parking.shared.core.model.ParkingZone
import nl.dennisvanderzalm.parking.shared.data.model.PaidParkingTimes

interface PaidParkingDataSource {

    fun getPaidParkingHours(zone: ParkingZone, localDate: LocalDate): PaidParkingTimes?
}
