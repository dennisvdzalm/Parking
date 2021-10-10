package nl.dennisvanderzalm.parking.ui

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.navigation.*
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
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

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ParkingContent(startDest: Screen) {
    val navController = rememberAnimatedNavController()

    Surface(color = MaterialTheme.colors.background) {
        AnimatedNavHost(navController = navController, startDestination = startDest.route) {
            composable(Screen.Login) {
                LoginScreen {
                    navController.navigate(Screen.ParkingOverview) {
                        popUpTo(Screen.Login) { inclusive = true }
                    }
                }
            }
            composable(Screen.ParkingOverview) { ParkingOverviewScreen { navController.navigate(Screen.CreateParking) } }
            composable(Screen.CreateParking) { CreateParkingReservationScreen { navController.popBackStack(Screen.ParkingOverview.route, false) } }
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
    enterTransition = { _, _ -> fadeIn(animationSpec = tween(700)) },
    exitTransition = { _, _ -> fadeOut(animationSpec = tween(700)) },
    popEnterTransition = { _, _ -> fadeIn(animationSpec = tween(700)) },
    popExitTransition = { _, _ -> fadeOut(animationSpec = tween(700)) },
    route = screen.route,
    arguments = arguments,
    deepLinks = deepLinks,
    content = content
)

fun NavOptionsBuilder.popUpTo(screen: Screen, popUpToBuilder: PopUpToBuilder.() -> Unit) =
    popUpTo(screen.route, popUpToBuilder)
