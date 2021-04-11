package nl.dennisvanderzalm.parking.ui.create

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.isFocused
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import kotlinx.datetime.*
import nl.dennisvanderzalm.parking.shared.core.models.DutchLicensePlateNumber
import nl.dennisvanderzalm.parking.shared.core.models.isLicensePlate
import nl.dennisvanderzalm.parking.shared.core.models.toLicensePlateNumber
import org.koin.androidx.compose.getViewModel
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import kotlin.math.roundToInt

@Composable
fun CreateParkingReservationScreen(onComplete: () -> Unit) {
    val viewModel: CreateParkingReservationViewModel = getViewModel()

    val state: CreateParkingReservationViewState by viewModel.state

    CreateParkingReservationContent(state) { from, until, licenseNumber, name ->
        viewModel.create(from, until, licenseNumber, name)
    }
}

@Composable
fun CreateParkingReservationContent(
    state: CreateParkingReservationViewState,
    onCreateParking: (
        from: Instant,
        until: Instant,
        licensePlate: DutchLicensePlateNumber,
        name: String
    ) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar {
                Text(
                    modifier = Modifier.padding(16.dp),
                    text = "Create reservation"
                )
            }
        },
        content = { ReservationDetails(state, onCreateParking) }
    )
}

@Composable
fun ReservationDetails(
    state: CreateParkingReservationViewState,
    onCreateParking: (
        from: Instant,
        until: Instant,
        licensePlate: DutchLicensePlateNumber,
        name: String
    ) -> Unit
) {
    when (state) {
        CreateParkingReservationViewState.Created -> TODO()
        CreateParkingReservationViewState.Loading -> TODO()
        CreateParkingReservationViewState.Create -> ReservationDetailsInput(onCreateParking)
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ReservationDetailsInput(
    onCreateParking: (
        from: Instant,
        until: Instant,
        licensePlate: DutchLicensePlateNumber,
        name: String
    ) -> Unit
) {
    val focusRequester = remember { FocusRequester() }

    val dateFormatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
    var licensePlate by rememberSaveable { mutableStateOf("") }
    var sliderPosition by rememberSaveable { mutableStateOf(0f) }

    val minutes = sliderPosition.roundToInt()

    val now = Clock.System.now()
    val end = now.plus(minutes, DateTimeUnit.MINUTE)

    val humanReadableEndDate =
        dateFormatter.format(end.toLocalDateTime(TimeZone.currentSystemDefault()).toJavaLocalDateTime())

    val period = now.periodUntil(end, TimeZone.currentSystemDefault())

    Column(
        Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            modifier = Modifier
                .onFocusChanged {
                    if (!it.isFocused && licensePlate.isLicensePlate()) {
                        licensePlate = licensePlate.toLicensePlateNumber().prettyNumber
                    }
                }
                .padding(16.dp),
            value = licensePlate,
            maxLines = 1,
            keyboardOptions = KeyboardOptions(
                autoCorrect = false,
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(onNext = { focusRequester.requestFocus() }),
            onValueChange = { licensePlate = it },
            label = { Text(text = "License plate") }
        )


        Slider(value = sliderPosition,
            valueRange = 0f..900f,
            onValueChange = { sliderPosition = it })

        Text(text = "${period.hours} hours ${period.minutes} minutes")
        Text(text = "Ends at $humanReadableEndDate")

        Button(
            modifier = Modifier.padding(16.dp),
            onClick = {
                onCreateParking(now, end, licensePlate.toLicensePlateNumber(), "")
            }
        ) {
            Text(text = "Create")
        }
    }
}
