package nl.dennisvanderzalm.parking


import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import by.kirich1409.viewbindingdelegate.viewBinding
import nl.dennisvanderzalm.parking.databinding.ActivityMainBinding
import nl.dennisvanderzalm.parking.licenseplatescanner.EXTRA_LICENSE_PLATE_RESULT
import nl.dennisvanderzalm.parking.licenseplatescanner.NumberPlateCaptureActivity
import nl.dennisvanderzalm.parking.login.LoginActivity

private const val REQUEST_CODE_NUMBERPLATE = 100

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val binding: ActivityMainBinding by viewBinding(ActivityMainBinding::bind, R.id.container)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.btnStartCapture.setOnClickListener {
            val intent = Intent(this, NumberPlateCaptureActivity::class.java)

            startActivityForResult(intent, REQUEST_CODE_NUMBERPLATE)
        }

        binding.btnLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)

            startActivity(intent)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE_NUMBERPLATE) {
            binding.tvNumberPlate.text = data?.getStringExtra(EXTRA_LICENSE_PLATE_RESULT)
        }
    }
}
