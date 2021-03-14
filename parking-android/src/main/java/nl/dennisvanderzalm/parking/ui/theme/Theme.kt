package nl.dennisvanderzalm.parking.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun ParkingTheme(
    isDark: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colors = if (isDark) ParkingDarkColors else ParkingLightColors,
        typography = ParkingTypography,
        shapes = ParkingShapes,
        content = content
    )
}
