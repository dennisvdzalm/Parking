package nl.dennisvanderzalm.parking.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import nl.dennisvanderzalm.parking.shared.core.model.StartupAction
import nl.dennisvanderzalm.parking.shared.core.usecase.GetStartupActionUseCase

class ParkingAppViewModel(private val startupActionUseCase: GetStartupActionUseCase) : ViewModel() {

    var state by mutableStateOf<AppViewState>(AppViewState.Bootstrapping)
        private set

    init{
        viewModelScope.launch {
            state = withContext(Dispatchers.IO) {
                startupActionUseCase.get(GetStartupActionUseCase.RequestValues).let {
                    when (it) {
                        StartupAction.ShowLogin -> AppViewState.Login
                        StartupAction.ShowOverview -> AppViewState.Overview
                    }
                }
            }
        }
    }
}

sealed class AppViewState {

    object Bootstrapping : AppViewState()

    object Login : AppViewState()

    object Overview : AppViewState()
}
