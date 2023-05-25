package nl.dennisvanderzalm.parking.shared.ui

import kotlinx.datetime.LocalDateTime

expect fun LocalDateTime.format(format: String): String