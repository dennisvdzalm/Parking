package nl.dennisvanderzalm.parking

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.serialization.Serializable
import nl.dennisvanderzalm.parking.core.model.Config
import nl.dennisvanderzalm.parking.core.model.DataSourceConfig
import nl.dennisvanderzalm.parking.core.model.StartupAction
import nl.dennisvanderzalm.parking.core.usecase.GetStartupActionUseCase
import nl.dennisvanderzalm.parking.ui.create.CreateParkingReservation
import nl.dennisvanderzalm.parking.ui.login.Login
import nl.dennisvanderzalm.parking.ui.parkingoverview.ParkingOverview
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinApplication
import org.koin.compose.koinInject
import org.koin.dsl.KoinAppDeclaration

private val config = Config(
    DataSourceConfig.Remote("parkeren.leiden.nl")
)

@Composable
fun App() {
    val startUseCase: GetStartupActionUseCase = koinInject()
    val viewState: NavDestination by remember {
        val state = when (startUseCase.invoke()) {
            StartupAction.ShowLogin -> Login
            StartupAction.ShowOverview -> Overview
        }
        mutableStateOf(state)
    }

    val navController = rememberNavController()
    NavHost(navController, startDestination = viewState) {
        composable<Overview> {
            ParkingOverview(
                onCreateParking = { navController.navigate(Create) }
            )
        }
        composable<Login> {
            Login(
                onLoginComplete = { navController.navigate(Overview) }
            )
        }
        composable<Create> {
            CreateParkingReservation(
                onBackPressed = { navController.navigate(Overview) }
            )
        }
    }
}

sealed class NavDestination

@Serializable
data object Login : NavDestination()

@Serializable
data object Overview : NavDestination()

@Serializable
data object Create : NavDestination()
