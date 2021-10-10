package nl.dennisvanderzalm.parking.ui.component

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable

@Composable
fun ParkingTopAppBar(
    title: String,
    onBackPressed: (() -> Unit)? = null,
) = TopAppBar(
    title = { Text(title) },
    navigationIcon = onBackPressed?.let {
        {
            IconButton(onClick = onBackPressed, enabled = true) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back"
                )
            }
        }
    }
)
