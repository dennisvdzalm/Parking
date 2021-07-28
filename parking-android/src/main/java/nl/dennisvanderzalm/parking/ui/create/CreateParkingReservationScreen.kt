package nl.dennisvanderzalm.parking.ui.create

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import kotlinx.datetime.*
import nl.dennisvanderzalm.parking.shared.core.model.AddressBookItem
import nl.dennisvanderzalm.parking.shared.core.model.DutchLicensePlateNumber
import nl.dennisvanderzalm.parking.shared.core.model.isLicensePlate
import nl.dennisvanderzalm.parking.shared.core.model.toLicensePlateNumber
import org.koin.androidx.compose.getViewModel
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import kotlin.math.roundToInt
import kotlin.time.ExperimentalTime

@Composable
fun CreateParkingReservationScreen(onComplete: () -> Unit) {
    val viewModel: CreateParkingReservationViewModel = getViewModel()

    CreateParkingReservationContent(viewModel.state, viewModel::create, viewModel::queryAddressBook)
}

@Composable
fun CreateParkingReservationContent(
    state: CreateParkingReservationViewState,
    onCreateParking: (
        from: Instant,
        until: Instant,
        licensePlate: DutchLicensePlateNumber,
        name: String
    ) -> Unit,
    queryAddressBook: (query: String) -> Unit
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
        content = { ReservationDetails(state, onCreateParking, queryAddressBook) }
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
    ) -> Unit,
    queryAddressBook: (query: String) -> Unit
) {
    when (state) {
        is CreateParkingReservationViewState.Created -> Text("Done")
        is CreateParkingReservationViewState.Loading -> CircularProgressIndicator()
        is CreateParkingReservationViewState.Create -> ReservationDetailsInput(
            state.suggestedAddressBookItems,
            onCreateParking,
            queryAddressBook
        )
        is CreateParkingReservationViewState.Error -> Text("Error: ${state.message}")
    }
}

@OptIn(
    ExperimentalComposeUiApi::class, ExperimentalTime::class,
    androidx.compose.animation.ExperimentalAnimationApi::class
)
@Composable
fun ReservationDetailsInput(
    suggestedAddressBookItems: List<AddressBookItem>,
    onCreateParking: (from: Instant, until: Instant, licensePlate: DutchLicensePlateNumber, name: String) -> Unit,
    queryAddressBook: (query: String) -> Unit
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

    val showSuggestions = !licensePlate.isLicensePlate() && licensePlate.isNotEmpty() && suggestedAddressBookItems.isNotEmpty()

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
            trailingIcon = {
                if (licensePlate.isNotEmpty()) {
                    Icon(
                        modifier = Modifier.clickable(
                            indication = rememberRipple(bounded = false),
                            enabled = true,
                            interactionSource = remember { MutableInteractionSource() },
                            onClick = { licensePlate = "" }
                        ),
                        imageVector = Icons.Default.Clear,
                        contentDescription = "Clear license input"
                    )
                }
            },
            keyboardActions = KeyboardActions(onNext = { focusRequester.requestFocus() }),
            onValueChange = {
                licensePlate = it.uppercase()
                queryAddressBook(it)
            },
            label = { Text(text = "License plate") }
        )

        AnimatedVisibility(visible = showSuggestions) {
            LazyColumn(horizontalAlignment = Alignment.CenterHorizontally) {
                items(suggestedAddressBookItems) { item ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { licensePlate = item.licensePlateNumber.prettyNumber }) {
                        Text(
                            modifier = Modifier
                                .padding(8.dp)
                                .weight(1f),
                            text = item.name,
                            textAlign = TextAlign.Start,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            modifier = Modifier
                                .padding(8.dp)
                                .weight(1f),
                            maxLines = 1,
                            textAlign = TextAlign.End,
                            text = item.licensePlateNumber.prettyNumber,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }
        }

        Slider(value = sliderPosition,
            valueRange = 0f..3200f,
            onValueChange = { sliderPosition = it })

        Text(text = "${period.days} days ${period.hours} hours ${period.minutes} minutes")
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
