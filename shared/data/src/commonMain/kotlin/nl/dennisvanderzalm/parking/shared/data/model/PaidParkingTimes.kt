package nl.dennisvanderzalm.parking.shared.data.model

import kotlinx.datetime.LocalDateTime

data class PaidParkingTimes(val start: LocalDateTime, val end: LocalDateTime)
