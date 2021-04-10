package nl.dennisvanderzalm.parking.ui.navigation

sealed class Screen(val route: String) {

    object Login : Screen("login")
    object ParkingOverview : Screen("parking_overview")
    object CreateParking : Screen("create_parking")
}