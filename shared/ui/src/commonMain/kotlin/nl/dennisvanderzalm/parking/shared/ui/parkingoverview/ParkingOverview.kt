package nl.dennisvanderzalm.parking.shared.ui.parkingoverview

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.persistentListOf
import nl.dennisvanderzalm.parking.shared.ui.Dependencies
import nl.dennisvanderzalm.parking.shared.ui.component.DutchLicensePlateComponent
import nl.dennisvanderzalm.parking.shared.ui.component.ParkingTopAppBar
import nl.dennisvanderzalm.parking.ui.parkingoverview.ParkingReservationUiModel

@Composable
fun ParkingOverview(onCreateParking: () -> Unit) {
    val scope = rememberCoroutineScope()
    val viewModel = remember {
        ParkingOverviewViewModel(
            scope,
            Dependencies.getParkingHistoryUseCase,
            Dependencies.endParkingHistoryUseCase
        )
    }

    LaunchedEffect(Unit) {
        viewModel.getParkingHistory()
    }

    ParkingOverviewContent(
        state = viewModel.state,
        onCreateParking = onCreateParking,
        onRefresh = viewModel::getParkingHistory,
        endReservation = viewModel::endReservation
    )
}

@Composable
private fun ParkingOverviewContent(
    state: ParkingOverviewViewState,
    onCreateParking: () -> Unit,
    onRefresh: () -> Unit,
    endReservation: (reservationId: Int) -> Unit
) {
    Scaffold(
        topBar = { ParkingTopAppBar(title = "Overview") },
        content = { paddingValues ->
            ParkingHistory(
                modifier = Modifier.padding(paddingValues),
                state = state,
                endReservation = endReservation,
                onRefresh = onRefresh,
                onCreateParking = onCreateParking,
            )
        }
    )
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterialApi::class)
@Composable
private fun ParkingHistory(
    state: ParkingOverviewViewState,
    onRefresh: () -> Unit,
    endReservation: (reservationId: Int) -> Unit,
    onCreateParking: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val listState = rememberLazyListState()
    val pullRefreshState = rememberPullRefreshState(
        refreshing = state.isLoading,
        onRefresh = onRefresh
    )
    Box(modifier = modifier.pullRefresh(pullRefreshState)) {
        LazyColumn(
            state = listState,
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        ) {
            item { CreateParkingSessionListItem(onCreateParking) }

            items(state.history, key = { it.key }) { historyItem ->
                ParkingHistoryItem(
                    modifier = Modifier.animateItemPlacement(),
                    item = historyItem,
                    endReservation = endReservation
                )
            }
        }
        PullRefreshIndicator(state.isLoading, pullRefreshState, Modifier.align(Alignment.TopCenter))
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
            OutlinedButton(onClick = onCreateParking) { Text("Create") }
        }


    }
}

@Composable
private fun ParkingHistoryItem(
    item: ParkingReservationUiModel,
    endReservation: (reservationId: Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
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
private fun ParkingOverviewPreview() {
    val history = persistentListOf(
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
        state = ParkingOverviewViewState(false, history),
        onCreateParking = {},
        onRefresh = {},
        endReservation = {}
    )
}
