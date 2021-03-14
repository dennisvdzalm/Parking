package nl.dennisvanderzalm.parking.ui

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigate
import androidx.navigation.compose.rememberNavController
import nl.dennisvanderzalm.parking.ui.login.LoginScreen
import nl.dennisvanderzalm.parking.ui.main.MainScreen
import nl.dennisvanderzalm.parking.ui.navigation.Screen

@Composable
@ExperimentalComposeUiApi
fun ParkingApp() {
    ParkingContent()
}

@Composable
@ExperimentalComposeUiApi
fun ParkingContent() {
    val navController = rememberNavController()

    Surface(color = MaterialTheme.colors.background) {
        NavHost(navController = navController, startDestination = Screen.Main.route) {
            composable(Screen.Login.route) { LoginScreen() }
            composable(Screen.Main.route) { MainScreen { navController.navigate(Screen.CreateParking) } }
            composable(Screen.CreateParking.route) { LoginScreen() }
        }
    }
}

private fun NavController.navigate(screen: Screen) = this.navigate(screen.route)
