package nl.dennisvanderzalm.parking.shared.data.source

import kotlinx.datetime.LocalDate
import nl.dennisvanderzalm.parking.shared.data.model.PaidParkingTimes

interface PaidParkingDataSource {

    fun getPaidParkingHours(localDate: LocalDate): PaidParkingTimes
}
