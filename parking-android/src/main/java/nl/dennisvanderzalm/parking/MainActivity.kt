package nl.dennisvanderzalm.parking


import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.ExperimentalComposeUiApi
import nl.dennisvanderzalm.parking.ui.ParkingApp
import nl.dennisvanderzalm.parking.ui.theme.ParkingTheme

private const val REQUEST_CODE_NUMBERPLATE = 100

class MainActivity : AppCompatActivity() {

//    private val binding: ActivityMainBinding by viewBinding(ActivityMainBinding::bind, R.id.container)

    @ExperimentalComposeUiApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ParkingTheme {
                ParkingApp()
            }
        }
//        binding.btnStartCapture.setOnClickListener {
//            val intent = Intent(this, NumberPlateCaptureActivity::class.java)
//
//            startActivityForResult(intent, REQUEST_CODE_NUMBERPLATE)
//        }

//        binding.btnLogin.setOnClickListener {
//            val intent = Intent(this, LoginActivity::class.java)
//
//            startActivity(intent)
//        }
    }
}
