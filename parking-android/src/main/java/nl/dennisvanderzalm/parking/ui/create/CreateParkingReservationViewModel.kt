package nl.dennisvanderzalm.parking.ui.create

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.datetime.Instant
import nl.dennisvanderzalm.parking.shared.core.model.AddressBookItem
import nl.dennisvanderzalm.parking.shared.core.model.DutchLicensePlateNumber
import nl.dennisvanderzalm.parking.shared.core.model.ParkingReservation
import nl.dennisvanderzalm.parking.shared.core.usecase.CreateParkingReservationUseCase
import nl.dennisvanderzalm.parking.shared.core.usecase.GetAddressBookUseCase
import nl.dennisvanderzalm.parking.shared.core.usecase.ResolveParkingReservationUseCase

class CreateParkingReservationViewModel(
    private val getAddressBookUseCase: GetAddressBookUseCase,
    private val createParkingReservationUseCase: CreateParkingReservationUseCase,
    private val resolveParkingReservationUseCase: ResolveParkingReservationUseCase
) : ViewModel() {

    var state by mutableStateOf<CreateParkingReservationViewState>(CreateParkingReservationViewState.Loading)
        private set

    private val _query = MutableStateFlow("")

    init {
        val addressBookFlow = flow { emit(getAddressBookUseCase.get(GetAddressBookUseCase.RequestValues)) }
            .flowOn(Dispatchers.IO)

        viewModelScope.launch {
            combine(
                addressBookFlow,
                _query
            ) { addressBook, query ->
                if (query.isBlank()) {
                    emptyList()
                } else {
                    addressBook.filter {
                        it.name.contains(query, true)
                                || it.licensePlateNumber.prettyNumber.contains(query, true)
                                || it.licensePlateNumber.rawNumber.contains(query, true)
                    }
                }
            }.collect { state = CreateParkingReservationViewState.Create(it) }
        }
    }

    fun queryAddressBook(query: String) = viewModelScope.launch { _query.emit(query) }

    fun create(
        respectPaidParkingHours: Boolean,
        from: Instant,
        until: Instant,
        licensePlateNumber: DutchLicensePlateNumber,
        name: String
    ) = viewModelScope.launch {
        flowOf<CreateParkingReservationViewState>(CreateParkingReservationViewState.Loading)
            .map {
                CreateParkingReservationViewState.Loading

                val requestValues = ResolveParkingReservationUseCase.RequestValues(
                    respectPaidParkingHours,
                    from,
                    until,
                    licensePlateNumber,
                    name
                )

                resolveParkingReservationUseCase.get(requestValues).forEach {
                    createParkingReservationUseCase.get(CreateParkingReservationUseCase.RequestValues(it))
                }

                CreateParkingReservationViewState.Created
            }
            .catch { CreateParkingReservationViewState.Error("Error creating parking reservation for ${licensePlateNumber.prettyNumber}") }
            .flowOn(Dispatchers.IO)
            .collect { state = it }
    }
}

sealed class CreateParkingReservationViewState {

    object Loading : CreateParkingReservationViewState()
    data class Create(val suggestedAddressBookItems: List<AddressBookItem>) : CreateParkingReservationViewState()
    data class Error(val message: String) : CreateParkingReservationViewState()
    object Created : CreateParkingReservationViewState()
}
