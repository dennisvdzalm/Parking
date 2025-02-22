package nl.dennisvanderzalm.parking

import kotlinx.datetime.LocalDateTime

expect fun LocalDateTime.format(format: String): String