package nl.dennisvanderzalm.parking.shared.ui.create.component

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.periodUntil
import kotlin.time.Duration

@Composable
fun DurationLabel(
    duration: Duration,
    modifier: Modifier = Modifier,
    from: Instant = remember { Clock.System.now() },
) {
    val period = remember(from, duration) {
        val end = from + duration
        from.periodUntil(end, TimeZone.currentSystemDefault())
    }

    Text(
        modifier = modifier,
        text = "${period.days} days ${period.hours} hours ${period.minutes} minutes"
    )
}