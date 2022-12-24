package nl.dennisvanderzalm.parking.ui

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.navigation.*
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import nl.dennisvanderzalm.parking.ui.create.CreateParkingReservation
import nl.dennisvanderzalm.parking.ui.login.Login
import nl.dennisvanderzalm.parking.ui.navigation.Screen
import nl.dennisvanderzalm.parking.ui.parkingoverview.ParkingOverview
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

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ParkingContent(startDest: Screen) {
    val navController = rememberAnimatedNavController()

    Surface(color = MaterialTheme.colors.background) {
        AnimatedNavHost(navController = navController, startDestination = startDest.route) {
            composable(Screen.Login) {
                Login {
                    navController.navigate(Screen.ParkingOverview) {
                        popUpTo(Screen.Login) { inclusive = true }
                    }
                }
            }
            composable(Screen.ParkingOverview) { ParkingOverview { navController.navigate(Screen.CreateParking) } }
            composable(Screen.CreateParking) { CreateParkingReservation { navController.popBackStack(Screen.ParkingOverview.route, false) } }
        }
    }
}

private fun NavController.navigate(screen: Screen, builder: NavOptionsBuilder.() -> Unit = {}) =
    navigate(screen.route, builder)

@OptIn(ExperimentalAnimationApi::class)
private fun NavGraphBuilder.composable(
    screen: Screen,
    arguments: List<NamedNavArgument> = emptyList(),
    deepLinks: List<NavDeepLink> = emptyList(),
    content: @Composable AnimatedVisibilityScope.(NavBackStackEntry) -> Unit
) = composable(
    route = screen.route,
    arguments = arguments,
    deepLinks = deepLinks,
    content = content
)

fun NavOptionsBuilder.popUpTo(screen: Screen, popUpToBuilder: PopUpToBuilder.() -> Unit) =
    popUpTo(screen.route, popUpToBuilder)
