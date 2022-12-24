package nl.dennisvanderzalm.parking.ui.parkingoverview

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.launch
import kotlinx.datetime.*
import nl.dennisvanderzalm.parking.ext.suspendRunCatching
import nl.dennisvanderzalm.parking.shared.core.model.ParkingHistoryItem
import nl.dennisvanderzalm.parking.shared.core.usecase.EndParkingReservationUseCase
import nl.dennisvanderzalm.parking.shared.core.usecase.GetParkingHistoryUseCase
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

class ParkingOverviewViewModel(
    private val getParkingHistoryUseCase: GetParkingHistoryUseCase,
    private val endParkingReservationUseCase: EndParkingReservationUseCase
) : ViewModel() {

    private val _state: MutableState<ParkingOverviewViewState> =
        mutableStateOf(ParkingOverviewViewState(true, persistentListOf()))
    val state: State<ParkingOverviewViewState> = _state

    private val dateFormatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)


    fun getParkingHistory() = viewModelScope.launch {
        _state.value = state.value.copy(isLoading = true)
        suspendRunCatching {
            val now = Clock.System.now()
            getParkingHistoryUseCase.get(GetParkingHistoryUseCase.RequestValues)
                .map { mapReservationItem(now, it) }
        }.onSuccess {
            _state.value = ParkingOverviewViewState(false, it.toPersistentList())
        }.onFailure {
            _state.value = state.value.copy(isLoading = false)
        }
    }

    fun endReservation(reservationId: Int) {
        viewModelScope.launch {
            endParkingReservationUseCase.get(EndParkingReservationUseCase.RequestValues(reservationId))
            getParkingHistory()
        }
    }

    private fun mapReservationItem(now: Instant, historyItem: ParkingHistoryItem): ParkingReservationUiModel {
        return if (historyItem.validUntil > now) {
            mapActiveItem(now, historyItem)
        } else {
            mapExpiredItem(historyItem)
        }
    }


    private fun mapActiveItem(now: Instant, historyItem: ParkingHistoryItem): ParkingReservationUiModel.Active {
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
        val from = dateFormatter.format(
            historyItem.validFrom.toLocalDateTime(TimeZone.currentSystemDefault()).toJavaLocalDateTime()
        )

        val until = dateFormatter.format(
            historyItem.validUntil.toLocalDateTime(TimeZone.currentSystemDefault()).toJavaLocalDateTime()
        )

        return ParkingReservationUiModel.Expired(
            historyItem.licensePlate?.prettyNumber,
            historyItem.reservationId,
            "$from - $until"
        )
    }
}

data class ParkingOverviewViewState(val isLoading: Boolean, val history: ImmutableList<ParkingReservationUiModel>)

