package nl.dennisvanderzalm.parking.ui.parkingoverview

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.datetime.*
import nl.dennisvanderzalm.parking.shared.core.models.ParkingReservation
import nl.dennisvanderzalm.parking.shared.core.usecases.EndParkingReservationUseCase
import nl.dennisvanderzalm.parking.shared.core.usecases.GetParkingHistoryUseCase
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

class ParkingOverviewViewModel(
    private val getParkingHistoryUseCase: GetParkingHistoryUseCase,
    private val endParkingReservationUseCase: EndParkingReservationUseCase
) : ViewModel() {

    private val _state: MutableState<ParkingOverviewViewState> = mutableStateOf(ParkingOverviewViewState(emptyList()))
    val state: State<ParkingOverviewViewState> = _state

    private val dateFormatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)

    fun getParkingHistory() = viewModelScope.launch {
        val now = Clock.System.now()

        _state.value = withContext(Dispatchers.IO) {
            getParkingHistoryUseCase.get(GetParkingHistoryUseCase.RequestValues)
                .map { mapReservationItem(now, it) }
                .let { ParkingOverviewViewState(it) }
        }
    }

    fun endReservation(reservationId: Int) = viewModelScope.launch {
        endParkingReservationUseCase.get(EndParkingReservationUseCase.RequestValues(reservationId))
        getParkingHistory()
    }

    private fun mapReservationItem(now: Instant, reservation: ParkingReservation): ParkingReservationUiModel {
        return if (reservation.validUntil > now) {
            mapActiveItem(now, reservation)
        } else {
            mapExpiredItem(reservation)
        }
    }


    private fun mapActiveItem(now: Instant, reservation: ParkingReservation): ParkingReservationUiModel.Active {
        val timeLeft: String =
            now.periodUntil(reservation.validUntil, TimeZone.currentSystemDefault())
                .let { "${it.hours}h, ${it.minutes}m left" }

        return ParkingReservationUiModel.Active(
            reservation.licensePlate?.prettyNumber.orEmpty(),
            reservation.reservationId,
            timeLeft
        )
    }

    private fun mapExpiredItem(reservation: ParkingReservation): ParkingReservationUiModel.Expired {
        val from = dateFormatter.format(
            reservation.validFrom.toLocalDateTime(TimeZone.currentSystemDefault()).toJavaLocalDateTime()
        )

        val until = dateFormatter.format(
            reservation.validUntil.toLocalDateTime(TimeZone.currentSystemDefault()).toJavaLocalDateTime()
        )

        return ParkingReservationUiModel.Expired(
            reservation.licensePlate?.prettyNumber,
            reservation.reservationId,
            "$from - $until"
        )
    }
}

data class ParkingOverviewViewState(val history: List<ParkingReservationUiModel>)

