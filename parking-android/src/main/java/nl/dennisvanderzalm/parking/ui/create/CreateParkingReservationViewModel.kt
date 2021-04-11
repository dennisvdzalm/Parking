package nl.dennisvanderzalm.parking.ui.create

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.datetime.Instant
import nl.dennisvanderzalm.parking.shared.core.models.DutchLicensePlateNumber
import nl.dennisvanderzalm.parking.shared.core.repositories.GuestParkingRepository

class CreateParkingReservationViewModel(private val guestParkingRepository: GuestParkingRepository) : ViewModel() {

    private val _state = mutableStateOf<CreateParkingReservationViewState>(CreateParkingReservationViewState.Create)
    val state: State<CreateParkingReservationViewState> = _state

    fun create(
        from: Instant,
        until: Instant,
        licensePlateNumber: DutchLicensePlateNumber,
        name: String
    ) = viewModelScope.launch {
        flowOf<CreateParkingReservationViewState>(CreateParkingReservationViewState.Loading)
            .map {
                guestParkingRepository.createParkingReservation(from, until, licensePlateNumber, name)
                CreateParkingReservationViewState.Created
            }
            .catch { CreateParkingReservationViewState.Error("Error creating parking reservation for ${licensePlateNumber.prettyNumber}") }
            .flowOn(Dispatchers.IO)
            .collect { _state.value = it }
    }
}

sealed class CreateParkingReservationViewState {

    object Created : CreateParkingReservationViewState()
    object Loading : CreateParkingReservationViewState()
    object Create : CreateParkingReservationViewState()
    data class Error(val message: String) : CreateParkingReservationViewState()
}
