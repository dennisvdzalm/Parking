package nl.dennisvanderzalm.parking.ui.parkingoverview

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import nl.dennisvanderzalm.parking.ui.component.DutchLicensePlateComponent
import org.koin.androidx.compose.getViewModel

@Composable
fun ParkingOverviewScreen(
    onCreateParking: () -> Unit,
) {
    val viewModel: ParkingOverviewViewModel = getViewModel()
    viewModel.getParkingHistory()

    val state: ParkingOverviewViewState by viewModel.state

    ParkingOverviewContent(state, onCreateParking, viewModel::endReservation)
}

@Composable
private fun ParkingOverviewContent(
    state: ParkingOverviewViewState,
    onCreateParking: () -> Unit,
    endReservation: (reservationId: Int) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar {
                Text(
                    modifier = Modifier.padding(16.dp),
                    text = "Parking"
                )
            }
        },
        content = { ParkingHistory(state, endReservation, onCreateParking) },
        floatingActionButtonPosition = FabPosition.End,
        isFloatingActionButtonDocked = false
    )
}

@Composable
private fun ParkingHistory(
    state: ParkingOverviewViewState,
    endReservation: (reservationId: Int) -> Unit,
    onCreateParking: () -> Unit
) {
    val listState = rememberLazyListState()
    LazyColumn(
        state = listState,
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
    ) {
        item { CreateParkingSessionListItem(onCreateParking) }

        items(state.history) { historyItem ->
            ParkingHistoryItem(historyItem, endReservation)
        }
    }
}

@Composable
private fun CreateParkingSessionListItem(onCreateParking: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp),
        border = BorderStroke(
            1.dp, Color.Gray
        ),
        elevation = 0.dp,
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                modifier = Modifier.padding(16.dp),
                text = "Create a parking reservation",
                style = MaterialTheme.typography.h5
            )
            Button(onClick = onCreateParking) {
                Text("Create")
            }
        }


    }
}

@Composable
private fun ParkingHistoryItem(
    item: ParkingReservationUiModel,
    endReservation: (reservationId: Int) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(PaddingValues(top = 8.dp)),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        when (item) {
            is ParkingReservationUiModel.Active -> ParkingHistoryActiveItem(item, endReservation)
            is ParkingReservationUiModel.Expired -> ParkingHistoryExpiredItem(item)
        }
    }
}

@Composable
private fun ParkingHistoryActiveItem(
    item: ParkingReservationUiModel.Active,
    endReservation: (reservationId: Int) -> Unit
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            DutchLicensePlateComponent(item.licensePlateNumber)
            Icon(
                modifier = Modifier.clickable { endReservation(item.reservationId) },
                imageVector = Icons.Default.Delete,
                contentDescription = "Cancel active reservation"
            )
        }
        Text(
            text = item.timeLeft,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}


@Composable
private fun ParkingHistoryExpiredItem(item: ParkingReservationUiModel.Expired) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        if (item.licensePlateNumber != null) {
            DutchLicensePlateComponent(item.licensePlateNumber)
        }
        Text(
            text = item.duration,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
@Preview
private fun ParkingOverviewPreview() {
    val history = listOf(
        ParkingReservationUiModel.Active(
            licensePlateNumber = "SR-850-S",
            reservationId = 1,
            timeLeft = "1h, 5m"
        ),
        ParkingReservationUiModel.Expired(
            "SR-850-S",
            1,
            "22-3-2021 - 22-3-2021",
        ),
        ParkingReservationUiModel.Expired(
            "SR-850-S",
            1,
            "22-3-2021 - 22-3-2021",
        ),
        ParkingReservationUiModel.Expired(
            "SR-850-S",
            1,
            "22-3-2021 - 22-3-2021",
        ),
        ParkingReservationUiModel.Expired(
            "SR-850-S",
            1,
            "22-3-2021 - 22-3-2021",
        ),
        ParkingReservationUiModel.Expired(
            "SR-850-S",
            1,
            "22-3-2021 - 22-3-2021",
        ),
        ParkingReservationUiModel.Expired(
            "SR-850-S",
            1,
            "22-3-2021 - 22-3-2021",
        )
    )
    ParkingOverviewContent(
        state = ParkingOverviewViewState(history),
        onCreateParking = {},
        endReservation = {}
    )
}
