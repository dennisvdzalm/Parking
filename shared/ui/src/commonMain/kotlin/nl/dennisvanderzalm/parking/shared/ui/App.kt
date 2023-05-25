package nl.dennisvanderzalm.parking.shared.ui

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import nl.dennisvanderzalm.parking.shared.core.model.StartupAction
import nl.dennisvanderzalm.parking.shared.ui.create.CreateParkingReservation
import nl.dennisvanderzalm.parking.shared.ui.login.Login
import nl.dennisvanderzalm.parking.shared.ui.parkingoverview.ParkingOverview

@Composable
internal fun App() {
    val startUseCase = remember { Dependencies.startupActionUseCase }
    var viewState: ViewState by remember {
        val state = when (startUseCase.invoke()) {
            StartupAction.ShowLogin -> ViewState.Login
            StartupAction.ShowOverview -> ViewState.Overview
        }
        mutableStateOf(state)
    }

    MaterialTheme {
        when (viewState) {
            ViewState.Login -> Login(
                onLoginComplete = { viewState = ViewState.Overview }
            )

            ViewState.Overview -> ParkingOverview(
                onCreateParking = { viewState = ViewState.Create }
            )

            ViewState.Create -> CreateParkingReservation(
                onBackPressed = { viewState = ViewState.Overview }
            )
        }
    }

}

private enum class ViewState {
    Login,
    Overview,
    Create
}