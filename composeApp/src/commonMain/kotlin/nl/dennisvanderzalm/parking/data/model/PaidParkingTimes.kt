package nl.dennisvanderzalm.parking.data.model

import kotlinx.datetime.LocalDateTime

data class PaidParkingTimes(val start: LocalDateTime, val end: LocalDateTime)
