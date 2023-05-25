package nl.dennisvanderzalm.parking.shared.ui.create.component

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import nl.dennisvanderzalm.parking.shared.ui.format


@Composable
fun DateLabel(
    date: Instant,
    modifier: Modifier = Modifier,
) {
    val humanReadableEndDate = remember(date) {
        date.toLocalDateTime(TimeZone.currentSystemDefault()).format("yyyy-MM-dd HH:mm")
    }
    Text(
        modifier = modifier,
        text = "Ends at $humanReadableEndDate"
    )
}