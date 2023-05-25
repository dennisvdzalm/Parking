package nl.dennisvanderzalm.parking.shared.ui.create

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import nl.dennisvanderzalm.parking.shared.core.ext.suspendRunCatching
import nl.dennisvanderzalm.parking.shared.core.model.AddressBookItem
import nl.dennisvanderzalm.parking.shared.core.model.DutchLicensePlateNumber
import nl.dennisvanderzalm.parking.shared.core.model.ParkingZone
import nl.dennisvanderzalm.parking.shared.core.model.isLicensePlate
import nl.dennisvanderzalm.parking.shared.core.usecase.CreateParkingReservationUseCase
import nl.dennisvanderzalm.parking.shared.core.usecase.GetAddressBookUseCase
import nl.dennisvanderzalm.parking.shared.core.usecase.ResolveParkingReservationUseCase
import kotlin.time.Duration

class CreateParkingReservationViewModel(
    private val scope: CoroutineScope,
    private val getAddressBookUseCase: GetAddressBookUseCase,
    private val createParkingReservationUseCase: CreateParkingReservationUseCase,
    private val resolveParkingReservationUseCase: ResolveParkingReservationUseCase
) {
    var addressBookState by mutableStateOf(AddressBookViewState())
        private set

    var buttonState by mutableStateOf(CreateButtonState())
        private set

    var navigateToOverview by mutableStateOf(false)
        private set

    private val _query = MutableStateFlow("")
    private var _currentlySelectedLicensePlate = MutableStateFlow<DutchLicensePlateNumber?>(null)
    private val _isLoading = MutableStateFlow(false)

    init {
        produceAddressBookState()
        produceButtonState()
    }

    private fun produceButtonState() {
        scope.launch {
            combine(_currentlySelectedLicensePlate, _isLoading) { selectedLicensePlate, isLoading ->
                CreateButtonState(
                    isEnabled = selectedLicensePlate != null,
                    showLoadingIndicator = isLoading
                )
            }.collect { buttonState = it }
        }
    }

    private fun produceAddressBookState() {
        val addressBookFlow = flow { emit(getAddressBookUseCase()) }
        scope.launch {
            combine(
                addressBookFlow,
                _query,
                _currentlySelectedLicensePlate
            ) { addressBook, query, currentlySelectedLicensePlate ->
                val addressBookItems = if (query.isBlank()) {
                    emptyList()
                } else {
                    addressBook.filter { item ->
                        val nameContainsQuery = item.name.contains(query, true)
                        val prettyNumberContainsQuery = item.licensePlateNumber.prettyNumber.contains(query, true)
                        val rawNumberContainsQuery = item.licensePlateNumber.rawNumber.contains(query, true)
                        return@filter nameContainsQuery || prettyNumberContainsQuery || rawNumberContainsQuery
                    }
                }

                val showSuggestion = query.isNotEmpty() && !query.isLicensePlate()

                AddressBookViewState(
                    isLoading = false,
                    showSuggestions = showSuggestion,
                    searchSuggestions = addressBookItems.toPersistentList(),
                    selectedEntry = currentlySelectedLicensePlate
                )
            }.collect { addressBookState = it }
        }
    }

    fun queryAddressBook(query: String) {
        scope.launch { _query.emit(query) }
    }

    fun onSelectLicensePlate(dutchLicensePlateNumber: DutchLicensePlateNumber?) {
        _currentlySelectedLicensePlate.value = dutchLicensePlateNumber
    }

    fun create(
        respectPaidParkingHours: Boolean,
        duration: Duration,
        zone: ParkingZone
    ) {
        if (_isLoading.value) return

        val now = Clock.System.now()
        val end = now + duration

        val selectedLicensePlate = requireNotNull(_currentlySelectedLicensePlate.value) {
            "License plate not selected"
        }

        scope.launch {
            _isLoading.emit(true)
            suspendRunCatching {
                resolveParkingReservationUseCase(
                    respectPaidParkingHours = respectPaidParkingHours,
                    start = now,
                    end = end,
                    licensePlateNumber = selectedLicensePlate,
                    name = "",
                    zone = zone
                ).forEach { createParkingReservationUseCase(it) }
            }.onSuccess {
                _isLoading.emit(false)
                navigateToOverview = true
            }.onFailure {
                _isLoading.emit(false)
            }
        }
    }
}


data class AddressBookViewState(
    val isLoading: Boolean = true,
    val showSuggestions: Boolean = false,
    val searchSuggestions: ImmutableList<AddressBookItem> = persistentListOf(),
    val selectedEntry: DutchLicensePlateNumber? = null
)

data class CreateButtonState(
    val isEnabled: Boolean = false,
    val showLoadingIndicator: Boolean = false
)
