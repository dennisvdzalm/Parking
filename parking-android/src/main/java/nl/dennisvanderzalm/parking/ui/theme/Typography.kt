package nl.dennisvanderzalm.parking.ui.theme

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.material.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview

val ParkingTypography = Typography()

@Preview(device = Devices.PIXEL_4, showSystemUi = true)
@Composable
private fun MomaThemeTypographyPreview() {
    Column {
        Text(text = "h1", style = ParkingTypography.h1)
        Text(text = "h2", style = ParkingTypography.h2)
        Text(text = "h3", style = ParkingTypography.h3)
        Text(text = "h4", style = ParkingTypography.h4)
        Text(text = "h5", style = ParkingTypography.h5)
        Text(text = "h6", style = ParkingTypography.h6)
        Text(text = "subtitle1", style = ParkingTypography.subtitle1)
        Text(text = "subtitle2", style = ParkingTypography.subtitle2)
        Text(text = "caption", style = ParkingTypography.caption)
        Text(text = "body1", style = ParkingTypography.body1)
        Text(text = "body2", style = ParkingTypography.body2)
        Text(text = "button", style = ParkingTypography.button)
        Text(text = "overline", style = ParkingTypography.overline)
    }
}
