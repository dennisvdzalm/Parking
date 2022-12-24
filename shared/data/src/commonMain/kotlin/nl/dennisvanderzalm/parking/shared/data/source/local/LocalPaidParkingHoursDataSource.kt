package nl.dennisvanderzalm.parking.shared.data.source.local

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import nl.dennisvanderzalm.parking.shared.core.model.ParkingZone
import nl.dennisvanderzalm.parking.shared.data.model.PaidParkingTimes
import nl.dennisvanderzalm.parking.shared.data.source.PaidParkingDataSource

class LocalPaidParkingDataSource : PaidParkingDataSource {

    override fun getPaidParkingHours(zone: ParkingZone, localDate: LocalDate): PaidParkingTimes? {
        val paidParkingWindow = zone.paidParkingTimes[localDate.dayOfWeek] ?: return null

        val start = LocalDateTime(
            year = localDate.year,
            monthNumber = localDate.monthNumber,
            dayOfMonth = localDate.dayOfMonth,
            hour = paidParkingWindow.startHour,
            minute = paidParkingWindow.startMinute,
            second = 0,
            nanosecond = 0
        )


        val end = LocalDateTime(
            year = localDate.year,
            monthNumber = localDate.monthNumber,
            dayOfMonth = localDate.dayOfMonth,
            hour = paidParkingWindow.endHour,
            minute = paidParkingWindow.endMinute,
            second = 0,
            nanosecond = 0
        )


        return PaidParkingTimes(start, end)
    }
}

private data class PaidParkingWindow(
    val startHour: Int,
    val startMinute: Int,
    val endHour: Int,
    val endMinute: Int
)