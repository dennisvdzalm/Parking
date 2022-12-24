package nl.dennisvanderzalm.parking.ui.create

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.toPersistentList
import nl.dennisvanderzalm.parking.shared.core.model.ParkingZone
import nl.dennisvanderzalm.parking.ui.theme.ParkingTheme

@Composable
fun ParkingZoneSelector(
    selectedParkingZone: ParkingZone,
    onParkingZoneSelected: (ParkingZone) -> Unit,
    modifier: Modifier = Modifier
) {
    var isExpanded by remember { mutableStateOf(false) }
    val shape = RoundedCornerShape(8.dp)

    Box(
        modifier
            .clip(shape)
            .border(BorderStroke(1.dp, Color.Black), shape)
            .clickable { isExpanded = !isExpanded }
            .padding(8.dp)
    ) {
        Text(text = selectedParkingZone.name)

        DropdownMenu(
            expanded = isExpanded,
            onDismissRequest = { isExpanded = !isExpanded }
        ) {
            val items = remember { ParkingZone.values().toList().toPersistentList() }

            for (item in items) {
                DropdownMenuItem(
                    modifier = Modifier.padding(8.dp),
                    onClick = {
                        onParkingZoneSelected(item)
                        isExpanded = !isExpanded
                    }
                ) {
                    Text(text = item.name)
                }
            }
        }
    }
}


@Preview
@Composable
private fun Preview() {
    ParkingTheme {
        ParkingZoneSelector(
            selectedParkingZone = ParkingZone.ZoneA,
            onParkingZoneSelected = {}
        )
    }
}