package nl.dennisvanderzalm.parking.ui.create

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.datetime.*
import nl.dennisvanderzalm.parking.shared.core.model.DutchLicensePlateNumber
import nl.dennisvanderzalm.parking.shared.core.model.ParkingZone
import nl.dennisvanderzalm.parking.ui.component.ParkingTopAppBar
import nl.dennisvanderzalm.parking.ui.create.component.AddressBookInputField
import nl.dennisvanderzalm.parking.ui.create.component.DateLabel
import nl.dennisvanderzalm.parking.ui.create.component.DurationLabel
import nl.dennisvanderzalm.parking.ui.create.component.DurationSlider
import nl.dennisvanderzalm.parking.ui.theme.ParkingTheme
import org.koin.androidx.compose.koinViewModel
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

@Composable
fun CreateParkingReservationScreen(onBackPressed: () -> Unit) {
    val viewModel: CreateParkingReservationViewModel = koinViewModel()

    LaunchedEffect(viewModel.navigateToOverview) {
        if (viewModel.navigateToOverview) onBackPressed()
    }

    CreateParkingReservationContent(
        addressBookState = viewModel.addressBookState,
        createButtonState = viewModel.buttonState,
        onSelectLicensePlate = viewModel::onSelectLicensePlate,
        onCreateParking = viewModel::create,
        queryAddressBook = viewModel::queryAddressBook,
        onBackPressed = onBackPressed
    )
}

@Composable
private fun CreateParkingReservationContent(
    addressBookState: AddressBookViewState,
    createButtonState: CreateButtonState,
    onSelectLicensePlate: (DutchLicensePlateNumber?) -> Unit,
    onCreateParking: (
        respectPaidParkingHours: Boolean,
        duration: Duration,
        zone: ParkingZone
    ) -> Unit,
    queryAddressBook: (query: String) -> Unit,
    onBackPressed: () -> Unit
) {
    Scaffold(
        topBar = {
            ParkingTopAppBar(
                title = "Create reservation",
                onBackPressed = onBackPressed
            )
        },
        content = {
            Box(Modifier.padding(it)) {
                ReservationDetails(
                    addressBookState = addressBookState,
                    createButtonState = createButtonState,
                    onSelectLicensePlate = onSelectLicensePlate,
                    onCreateParking = onCreateParking,
                    queryAddressBook = queryAddressBook,
                )
            }
        }
    )
}

@Composable
private fun ReservationDetails(
    addressBookState: AddressBookViewState,
    createButtonState: CreateButtonState,
    onSelectLicensePlate: (DutchLicensePlateNumber?) -> Unit,
    onCreateParking: (
        respectPaidParkingHours: Boolean,
        duration: Duration,
        zone: ParkingZone
    ) -> Unit,
    queryAddressBook: (query: String) -> Unit,
) {
    var selectedParkingZone by rememberSaveable { mutableStateOf(ParkingZone.ZoneA) }
    var duration by remember { mutableStateOf(0.seconds) }
    var respectPaidParkingHours by rememberSaveable { mutableStateOf(true) }

    Column(
        modifier = Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AddressBookInputField(
            state = addressBookState,
            onUpdateSearchQuery = queryAddressBook,
            onSelectLicensePlate = onSelectLicensePlate
        )

        DurationLabel(duration = duration)
        DateLabel(date = Clock.System.now() + duration)

        DurationSlider(onDurationChange = { duration = it })

        Spacer(modifier = Modifier.weight(1f))

        Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = "Would you like to book this reservation in paid parking hours only?"
                )
                Spacer(modifier = Modifier.width(16.dp))
                Switch(
                    modifier = Modifier.wrapContentSize(),
                    checked = respectPaidParkingHours,
                    onCheckedChange = { respectPaidParkingHours = !respectPaidParkingHours }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            AnimatedVisibility(visible = respectPaidParkingHours) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier.weight(1f),
                        text = "Which parking zone?"
                    )

                    ParkingZoneSelector(
                        selectedParkingZone = selectedParkingZone,
                        onParkingZoneSelected = { selectedParkingZone = it }
                    )
                }
            }
        }


        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            shape = MaterialTheme.shapes.medium,
            enabled = createButtonState.isEnabled,
            onClick = { onCreateParking(respectPaidParkingHours, duration, selectedParkingZone) },
            content = {
                Text(text = "Create")
                if (createButtonState.showLoadingIndicator) {
                    Spacer(modifier = Modifier.width(8.dp))
                    CircularProgressIndicator(
                        modifier = Modifier.size(16.dp),
                        strokeWidth = 2.dp,
                        color = Color.White
                    )
                }
            }
        )
    }
}

@Preview
@Composable
private fun Preview() {
    ParkingTheme {
        CreateParkingReservationContent(
            addressBookState = AddressBookViewState(),
            createButtonState = CreateButtonState(),
            onSelectLicensePlate = {},
            onCreateParking = { _, _, _ -> },
            queryAddressBook = {},
            onBackPressed = {},
        )
    }
}
