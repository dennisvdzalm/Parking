package nl.dennisvanderzalm.parking.ui

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.navigation.*
import androidx.navigation.compose.*
import nl.dennisvanderzalm.parking.ui.create.CreateParkingReservationScreen
import nl.dennisvanderzalm.parking.ui.login.LoginScreen
import nl.dennisvanderzalm.parking.ui.navigation.Screen
import nl.dennisvanderzalm.parking.ui.parkingoverview.ParkingOverviewScreen
import org.koin.androidx.compose.getViewModel

@Composable
fun ParkingApp() {
    val viewModel: ParkingAppViewModel = getViewModel()

    val dest = when (viewModel.state) {
        AppViewState.Bootstrapping -> null
        AppViewState.Login -> Screen.Login
        AppViewState.Overview -> Screen.ParkingOverview
    }

    if (dest != null) {
        ParkingContent(dest)
    }
}

@Composable
fun ParkingContent(startDest: Screen) {
    val navController = rememberNavController()

    Surface(color = MaterialTheme.colors.background) {
        NavHost(navController = navController, startDestination = startDest.route) {
            composable(Screen.Login) {
                LoginScreen {
                    navController.navigate(Screen.ParkingOverview) {
                        popUpTo(Screen.Login) { inclusive = true }
                    }
                }
            }
            composable(Screen.ParkingOverview) { ParkingOverviewScreen { navController.navigate(Screen.CreateParking) } }
            composable(Screen.CreateParking) { CreateParkingReservationScreen { navController.popBackStack() } }
        }
    }
}

private fun NavController.navigate(screen: Screen, builder: NavOptionsBuilder.() -> Unit = {}) =
    navigate(screen.route, builder)

private fun NavGraphBuilder.composable(
    screen: Screen,
    arguments: List<NamedNavArgument> = emptyList(),
    deepLinks: List<NavDeepLink> = emptyList(),
    content: @Composable (NavBackStackEntry) -> Unit
) = composable(screen.route, arguments, deepLinks, content)

fun NavOptionsBuilder.popUpTo(screen: Screen, popUpToBuilder: PopUpToBuilder.() -> Unit) =
    popUpTo(screen.route, popUpToBuilder)
