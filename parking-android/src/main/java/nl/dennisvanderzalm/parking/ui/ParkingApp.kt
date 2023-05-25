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
import nl.dennisvanderzalm.parking.ui.navigation.Screen
import nl.dennisvanderzalm.parking.shared.ui.parkingoverview.ParkingOverview

@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun ParkingContent(startDest: Screen) {
    val navController = rememberAnimatedNavController()

    Surface(color = MaterialTheme.colors.background) {
        AnimatedNavHost(navController = navController, startDestination = startDest.route) {
            composable(Screen.Login) {
            }
            composable(Screen.ParkingOverview) { ParkingOverview { navController.navigate(Screen.CreateParking) } }
            composable(Screen.CreateParking) { }
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
