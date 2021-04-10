package nl.dennisvanderzalm.parking.ui.parkingoverview

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.datetime.*
import nl.dennisvanderzalm.parking.shared.core.usecases.GetParkingHistoryUseCase
import java.time.Period
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import kotlin.time.Duration
import kotlin.time.ExperimentalTime
import kotlin.time.toJavaDuration

class ParkingOverviewViewModel(private val getParkingHistoryUseCase: GetParkingHistoryUseCase) : ViewModel() {

    private val _state: MutableState<ParkingOverviewViewState> = mutableStateOf(ParkingOverviewViewState(emptyList()))
    val state: State<ParkingOverviewViewState> = _state

    private val dateFormatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)

    @OptIn(ExperimentalTime::class)
    fun getParkingHistory() = viewModelScope.launch {
        _state.value = withContext(Dispatchers.IO) {
            val now = Clock.System.now()

            ParkingOverviewViewState(
                getParkingHistoryUseCase.get(GetParkingHistoryUseCase.RequestValues).map { reservation ->
                    val validUntilInstant = reservation.validUntil

                    if (validUntilInstant > now) {
                        val timeLeft: () -> String = {
                            validUntilInstant.periodUntil(Clock.System.now(), TimeZone.currentSystemDefault())
                                .let { "${it.hours}h, ${it.minutes}m left" }
                        }

                        ParkingReservationUiModel.Active(
                            reservation.licensePlate?.prettyNumber,
                            reservation.reservationId,
                            timeLeft
                        )
                    } else {
                        ParkingReservationUiModel.Expired(
                            reservation.licensePlate?.prettyNumber,
                            reservation.reservationId,
                            "${
                                dateFormatter.format(
                                    reservation.validFrom.toLocalDateTime(TimeZone.currentSystemDefault())
                                        .toJavaLocalDateTime()
                                )
                            } - ${
                                dateFormatter.format(
                                    reservation.validUntil.toLocalDateTime(TimeZone.currentSystemDefault())
                                        .toJavaLocalDateTime()
                                )
                            }"
                        )
                    }
                }
            )
        }
    }
}

data class ParkingOverviewViewState(val history: List<ParkingReservationUiModel>)

