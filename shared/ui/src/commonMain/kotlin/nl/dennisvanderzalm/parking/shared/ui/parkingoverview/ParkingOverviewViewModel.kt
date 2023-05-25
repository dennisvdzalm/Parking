package nl.dennisvanderzalm.parking.shared.ui.parkingoverview

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.datetime.*
import nl.dennisvanderzalm.parking.shared.core.ext.suspendRunCatching
import nl.dennisvanderzalm.parking.shared.core.model.ParkingHistoryItem
import nl.dennisvanderzalm.parking.shared.core.usecase.EndParkingReservationUseCase
import nl.dennisvanderzalm.parking.shared.core.usecase.GetParkingHistoryUseCase
import nl.dennisvanderzalm.parking.shared.ui.format
import nl.dennisvanderzalm.parking.ui.parkingoverview.ParkingReservationUiModel

class ParkingOverviewViewModel(
    private val scope: CoroutineScope,
    private val getParkingHistoryUseCase: GetParkingHistoryUseCase,
    private val endParkingReservationUseCase: EndParkingReservationUseCase
) {

    var state: ParkingOverviewViewState by mutableStateOf(
        ParkingOverviewViewState(
            true,
            persistentListOf()
        )
    )
        private set

    fun getParkingHistory() = scope.launch {
        state = state.copy(isLoading = true)
        suspendRunCatching {
            val now = Clock.System.now()
            getParkingHistoryUseCase().map { mapReservationItem(now, it) }
        }.onSuccess {
            state = ParkingOverviewViewState(false, it.toPersistentList())
        }.onFailure {
            state = state.copy(isLoading = false)
        }
    }

    fun endReservation(reservationId: Int) {
        scope.launch {
            endParkingReservationUseCase(reservationId)
            getParkingHistory()
        }
    }

    private fun mapReservationItem(
        now: Instant,
        historyItem: ParkingHistoryItem
    ): ParkingReservationUiModel {
        return if (historyItem.validUntil > now) {
            mapActiveItem(now, historyItem)
        } else {
            mapExpiredItem(historyItem)
        }
    }


    private fun mapActiveItem(
        now: Instant,
        historyItem: ParkingHistoryItem
    ): ParkingReservationUiModel.Active {
        val timeLeft: String =
            now.periodUntil(historyItem.validUntil, TimeZone.currentSystemDefault())
                .let { "${it.hours}h, ${it.minutes}m left" }

        return ParkingReservationUiModel.Active(
            historyItem.licensePlate?.prettyNumber.orEmpty(),
            historyItem.reservationId,
            timeLeft
        )
    }

    private fun mapExpiredItem(historyItem: ParkingHistoryItem): ParkingReservationUiModel.Expired {
        val from =
            historyItem.validFrom.toLocalDateTime(TimeZone.currentSystemDefault()).format()

        val until =
            historyItem.validUntil.toLocalDateTime(TimeZone.currentSystemDefault()).format()


        return ParkingReservationUiModel.Expired(
            historyItem.licensePlate?.prettyNumber,
            historyItem.reservationId,
            "$from - $until"
        )
    }
}

data class ParkingOverviewViewState(
    val isLoading: Boolean,
    val history: ImmutableList<ParkingReservationUiModel>
)

private fun LocalDateTime.format() = format("yyyy-MM-dd'T'HH:mm:ssZZ.")

