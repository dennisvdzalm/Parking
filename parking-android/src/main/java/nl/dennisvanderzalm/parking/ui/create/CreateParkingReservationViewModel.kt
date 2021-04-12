package nl.dennisvanderzalm.parking.ui.create

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.datetime.Instant
import nl.dennisvanderzalm.parking.shared.core.model.DutchLicensePlateNumber
import nl.dennisvanderzalm.parking.shared.core.usecase.CreateParkingReservationUseCase
import nl.dennisvanderzalm.parking.shared.core.usecase.ResolveParkingReservationUseCase

class CreateParkingReservationViewModel(
    private val createParkingReservationUseCase: CreateParkingReservationUseCase,
    private val resolveParkingReservationUseCase: ResolveParkingReservationUseCase
) : ViewModel() {

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
                CreateParkingReservationViewState.Loading

                resolveParkingReservationUseCase.get(
                    ResolveParkingReservationUseCase.RequestValues(from, until, licensePlateNumber, name)
                ).forEach { createParkingReservationUseCase.get(CreateParkingReservationUseCase.RequestValues(it)) }

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
