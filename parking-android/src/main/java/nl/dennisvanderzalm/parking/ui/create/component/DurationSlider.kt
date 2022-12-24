package nl.dennisvanderzalm.parking.ui.create.component

import androidx.compose.material.Slider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import kotlin.math.roundToInt
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes

@Composable
fun DurationSlider(
    onDurationChange: (Duration) -> Unit,
) {
    var sliderPosition by rememberSaveable { mutableStateOf(0f) }

    val duration = sliderPosition.roundToInt().minutes

    LaunchedEffect(duration) { onDurationChange(duration) }

    Slider(
        value = sliderPosition,
        valueRange = 0f..3200f,
        onValueChange = { sliderPosition = it }
    )
}