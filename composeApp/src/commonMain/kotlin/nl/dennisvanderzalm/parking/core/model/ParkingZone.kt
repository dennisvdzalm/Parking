package nl.dennisvanderzalm.parking.core.model

import kotlinx.datetime.DayOfWeek

enum class ParkingZone(val paidParkingTimes: Map<DayOfWeek, nl.dennisvanderzalm.parking.core.model.PaidParkingWindow?>) {

    ZoneA(
        mapOf(
            DayOfWeek.MONDAY to nl.dennisvanderzalm.parking.core.model.PaidParkingWindow(
                startHour = 0,
                startMinute = 0,
                endHour = 23,
                endMinute = 59
            ),
            DayOfWeek.TUESDAY to nl.dennisvanderzalm.parking.core.model.PaidParkingWindow(
                startHour = 0,
                startMinute = 0,
                endHour = 23,
                endMinute = 59
            ),
            DayOfWeek.WEDNESDAY to nl.dennisvanderzalm.parking.core.model.PaidParkingWindow(
                startHour = 0,
                startMinute = 0,
                endHour = 23,
                endMinute = 59
            ),
            DayOfWeek.THURSDAY to nl.dennisvanderzalm.parking.core.model.PaidParkingWindow(
                startHour = 0,
                startMinute = 0,
                endHour = 23,
                endMinute = 59
            ),
            DayOfWeek.FRIDAY to nl.dennisvanderzalm.parking.core.model.PaidParkingWindow(
                startHour = 0,
                startMinute = 0,
                endHour = 23,
                endMinute = 59
            ),
            DayOfWeek.SATURDAY to nl.dennisvanderzalm.parking.core.model.PaidParkingWindow(
                startHour = 0,
                startMinute = 0,
                endHour = 23,
                endMinute = 59
            ),
            DayOfWeek.SUNDAY to nl.dennisvanderzalm.parking.core.model.PaidParkingWindow(
                startHour = 0,
                startMinute = 0,
                endHour = 23,
                endMinute = 59
            ),
        )
    ),

    ZoneB1(
        mapOf(
            DayOfWeek.MONDAY to nl.dennisvanderzalm.parking.core.model.PaidParkingWindow(
                startHour = 9,
                startMinute = 0,
                endHour = 19,
                endMinute = 30
            ),
            DayOfWeek.TUESDAY to nl.dennisvanderzalm.parking.core.model.PaidParkingWindow(
                startHour = 9,
                startMinute = 0,
                endHour = 19,
                endMinute = 30
            ),
            DayOfWeek.WEDNESDAY to nl.dennisvanderzalm.parking.core.model.PaidParkingWindow(
                startHour = 9,
                startMinute = 0,
                endHour = 19,
                endMinute = 30
            ),
            DayOfWeek.THURSDAY to nl.dennisvanderzalm.parking.core.model.PaidParkingWindow(
                startHour = 9,
                startMinute = 0,
                endHour = 19,
                endMinute = 30
            ),
            DayOfWeek.FRIDAY to nl.dennisvanderzalm.parking.core.model.PaidParkingWindow(
                startHour = 9,
                startMinute = 0,
                endHour = 19,
                endMinute = 30
            ),
            DayOfWeek.SATURDAY to nl.dennisvanderzalm.parking.core.model.PaidParkingWindow(
                startHour = 9,
                startMinute = 0,
                endHour = 19,
                endMinute = 30
            ),
            DayOfWeek.SUNDAY to null
        )
    ),

    ZoneB2(
        mapOf(
            DayOfWeek.MONDAY to nl.dennisvanderzalm.parking.core.model.PaidParkingWindow(
                startHour = 9,
                startMinute = 0,
                endHour = 19,
                endMinute = 30
            ),
            DayOfWeek.TUESDAY to nl.dennisvanderzalm.parking.core.model.PaidParkingWindow(
                startHour = 9,
                startMinute = 0,
                endHour = 19,
                endMinute = 30
            ),
            DayOfWeek.WEDNESDAY to nl.dennisvanderzalm.parking.core.model.PaidParkingWindow(
                startHour = 9,
                startMinute = 0,
                endHour = 19,
                endMinute = 30
            ),
            DayOfWeek.THURSDAY to nl.dennisvanderzalm.parking.core.model.PaidParkingWindow(
                startHour = 9,
                startMinute = 0,
                endHour = 19,
                endMinute = 30
            ),
            DayOfWeek.FRIDAY to nl.dennisvanderzalm.parking.core.model.PaidParkingWindow(
                startHour = 9,
                startMinute = 0,
                endHour = 19,
                endMinute = 30
            ),
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