package nl.dennisvanderzalm.parking.ui.main

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable

@Composable
fun MainScreen(onCreateParking: () -> Unit) {
    Scaffold(
        topBar = { TopAppBar { Text(text = "Parking") } },
        content = { MainContent() },
        bottomBar = { BottomBar() },
        floatingActionButton = {
            FloatingActionButton(onClick = { onCreateParking() }) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null
                )
            }
        },
        floatingActionButtonPosition = FabPosition.End,
        isFloatingActionButtonDocked = false
    )
}


@Composable
fun MainContent() {

}

@Composable
fun BottomBar() {
    BottomNavigation() {
        BottomNavigationItem(
            selected = true,
            onClick = { },
            icon = { Icon(imageVector = Icons.Default.Home, null) }
        )
        BottomNavigationItem(
            selected = true,
            onClick = { },
            icon = { Icon(imageVector = Icons.Default.History, null) }
        )
    }
}