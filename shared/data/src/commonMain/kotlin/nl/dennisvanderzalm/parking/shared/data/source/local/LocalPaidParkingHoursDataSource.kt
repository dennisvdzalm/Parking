package nl.dennisvanderzalm.parking.shared.data.source.local

import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import nl.dennisvanderzalm.parking.shared.data.model.PaidParkingTimes
import nl.dennisvanderzalm.parking.shared.data.source.PaidParkingDataSource

class LocalPaidParkingDataSource : PaidParkingDataSource {

    private val paidParkingTimes = mapOf(
        DayOfWeek.MONDAY to Pair(9, 21),
        DayOfWeek.TUESDAY to Pair(9, 21),
        DayOfWeek.WEDNESDAY to Pair(9, 21),
        DayOfWeek.THURSDAY to Pair(9, 21),
        DayOfWeek.FRIDAY to Pair(9, 21),
        DayOfWeek.SATURDAY to Pair(9, 21),
        DayOfWeek.SUNDAY to Pair(13, 21)
    )

    override fun getPaidParkingHours(localDate: LocalDate): PaidParkingTimes {
        val start = requireNotNull(paidParkingTimes[localDate.dayOfWeek]?.let {
            LocalDateTime(localDate.year, localDate.monthNumber, localDate.dayOfMonth, it.first, 0, 0, 0)
        }) { "Paid parking start date unknown" }

        val end = requireNotNull(paidParkingTimes[localDate.dayOfWeek]?.let {
            LocalDateTime(localDate.year, localDate.monthNumber, localDate.dayOfMonth, it.second, 0, 0, 0)
        }) { "Paid parking end date unknown" }

        return PaidParkingTimes(start, end)
    }
}
