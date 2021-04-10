package nl.dennisvanderzalm.parking.ui.parkingoverview

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun LicensePlateComponent(
    licensePlateVariant: LicensePlateVariant,
    licencePlateNumber: String
) {
    Surface(
        modifier = Modifier
            .padding(16.dp)
            .width(200.dp)
            .height(50.dp),
        shape = MaterialTheme.shapes.medium,
        color = Color.Yellow,
        elevation = 0.dp,
        border = BorderStroke(1.dp, Color.Black),
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .background(Color.Blue)
                    .padding(8.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "NL",
                    color = Color.White
                )
            }
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                text = licencePlateNumber,
                textAlign = TextAlign.Center,
                color = Color.Black,
                style = MaterialTheme.typography.h5
            )
        }
    }
}

@Preview
@Composable
private fun LicensePlatePreview() {
    Column(
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        LicensePlateComponent(LicensePlateVariant.Nl, "SR-850-S")
        LicensePlateComponent(LicensePlateVariant.Nl, "K-517-VR")
        LicensePlateComponent(LicensePlateVariant.Nl, "14-SHR-1")
        LicensePlateComponent(LicensePlateVariant.Nl, "62-FV-BB")
    }

}

enum class LicensePlateVariant { Nl }
