package nl.dennisvanderzalm.parking.ui.create.component

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toJavaLocalDateTime
import kotlinx.datetime.toLocalDateTime
import nl.dennisvanderzalm.parking.ui.theme.ParkingTheme
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

@Composable
fun DateLabel(
    date: Instant,
    modifier: Modifier = Modifier,
    formatter: DateTimeFormatter = remember { DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM) },
) {
    val humanReadableEndDate = remember(date, formatter) {
        formatter.format(date.toLocalDateTime(TimeZone.currentSystemDefault()).toJavaLocalDateTime())
    }
    Text(
        modifier = modifier,
        text = "Ends at $humanReadableEndDate"
    )
}

@Preview
@Composable
private fun Preview() {
    ParkingTheme {
        DateLabel(Clock.System.now())
    }
}