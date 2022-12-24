package nl.dennisvanderzalm.parking.shared.core.model

import kotlinx.datetime.DayOfWeek

enum class ParkingZone(val paidParkingTimes: Map<DayOfWeek, PaidParkingWindow?>) {

    ZoneA(
        mapOf(
            DayOfWeek.MONDAY to PaidParkingWindow(startHour = 0, startMinute = 0, endHour = 23, endMinute = 59),
            DayOfWeek.TUESDAY to PaidParkingWindow(startHour = 0, startMinute = 0, endHour = 23, endMinute = 59),
            DayOfWeek.WEDNESDAY to PaidParkingWindow(startHour = 0, startMinute = 0, endHour = 23, endMinute = 59),
            DayOfWeek.THURSDAY to PaidParkingWindow(startHour = 0, startMinute = 0, endHour = 23, endMinute = 59),
            DayOfWeek.FRIDAY to PaidParkingWindow(startHour = 0, startMinute = 0, endHour = 23, endMinute = 59),
            DayOfWeek.SATURDAY to PaidParkingWindow(startHour = 0, startMinute = 0, endHour = 23, endMinute = 59),
            DayOfWeek.SUNDAY to PaidParkingWindow(startHour = 0, startMinute = 0, endHour = 23, endMinute = 59),
        )
    ),

    ZoneB1(
        mapOf(
            DayOfWeek.MONDAY to PaidParkingWindow(startHour = 9, startMinute = 0, endHour = 19, endMinute = 30),
            DayOfWeek.TUESDAY to PaidParkingWindow(startHour = 9, startMinute = 0, endHour = 19, endMinute = 30),
            DayOfWeek.WEDNESDAY to PaidParkingWindow(startHour = 9, startMinute = 0, endHour = 19, endMinute = 30),
            DayOfWeek.THURSDAY to PaidParkingWindow(startHour = 9, startMinute = 0, endHour = 19, endMinute = 30),
            DayOfWeek.FRIDAY to PaidParkingWindow(startHour = 9, startMinute = 0, endHour = 19, endMinute = 30),
            DayOfWeek.SATURDAY to PaidParkingWindow(startHour = 9, startMinute = 0, endHour = 19, endMinute = 30),
            DayOfWeek.SUNDAY to null
        )
    ),

    ZoneB2(
        mapOf(
            DayOfWeek.MONDAY to PaidParkingWindow(startHour = 9, startMinute = 0, endHour = 19, endMinute = 30),
            DayOfWeek.TUESDAY to PaidParkingWindow(startHour = 9, startMinute = 0, endHour = 19, endMinute = 30),
            DayOfWeek.WEDNESDAY to PaidParkingWindow(startHour = 9, startMinute = 0, endHour = 19, endMinute = 30),
            DayOfWeek.THURSDAY to PaidParkingWindow(startHour = 9, startMinute = 0, endHour = 19, endMinute = 30),
            DayOfWeek.FRIDAY to PaidParkingWindow(startHour = 9, startMinute = 0, endHour = 19, endMinute = 30),
            DayOfWeek.SATURDAY to null,
            DayOfWeek.SUNDAY to null
        )
    ),
}

data class PaidParkingWindow(
    val startHour: Int,
    val startMinute: Int,
    val endHour: Int,
    val endMinute: Int
)