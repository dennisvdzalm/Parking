package nl.dennisvanderzalm.parking


import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import nl.dennisvanderzalm.parking.shared.ui.ParkingApp
import nl.dennisvanderzalm.parking.ui.theme.ParkingTheme

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ParkingTheme {
                ParkingApp()
            }
        }
    }
}
