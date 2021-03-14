package nl.dennisvanderzalm.parking.ui.navigation

sealed class Screen(val route: String) {

    object Login : Screen("login")
    object Main : Screen("main")
    object CreateParking : Screen("create_parking")
}