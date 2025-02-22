package nl.dennisvanderzalm.parking

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toJavaLocalDateTime
import java.time.format.DateTimeFormatter

actual fun LocalDateTime.format(
    format: String
): String = DateTimeFormatter.ofPattern(format).format(this.toJavaLocalDateTime())