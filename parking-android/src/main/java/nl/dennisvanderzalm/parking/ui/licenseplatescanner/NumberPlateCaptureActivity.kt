package nl.dennisvanderzalm.parking.ui.licenseplatescanner

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.DisplayMetrics
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.AspectRatio
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.common.util.concurrent.ListenableFuture
import nl.dennisvanderzalm.parking.R
import nl.dennisvanderzalm.parking.databinding.ActivityNumberPlateCaptureBinding
import nl.dennisvanderzalm.parking.licenseplatescanner.LicensePlateAnalyser
import timber.log.Timber
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

const val EXTRA_LICENSE_PLATE_RESULT = "license_plate_result"

private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
private const val REQUEST_CODE_PERMISSIONS = 100

class NumberPlateCaptureActivity : AppCompatActivity(R.layout.activity_number_plate_capture),
    LicensePlateAnalyser.LicencePlateResultListener {

    private val binding: ActivityNumberPlateCaptureBinding by viewBinding(
        ActivityNumberPlateCaptureBinding::bind,
        R.id.container
    )

    private lateinit var cameraExecutor: ExecutorService

    private lateinit var cameraProviderFuture: ListenableFuture<ProcessCameraProvider>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        cameraExecutor = Executors.newSingleThreadExecutor()
        cameraProviderFuture = ProcessCameraProvider.getInstance(this)

        if (areAllPermissionsGranted()) {
            startCamera()
        } else {
            requestPermissions(REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (areAllPermissionsGranted()) {
                startCamera()
            } else {
                finish()
            }
        }
    }

    private fun startCamera() {
        cameraProviderFuture.addListener({
            val cameraProvider = cameraProviderFuture.get()
            bindPreview(cameraProvider)
        }, ContextCompat.getMainExecutor(this))
    }

    private fun areAllPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(this, it) == PackageManager.PERMISSION_GRANTED
    }

    private fun bindPreview(cameraProvider: ProcessCameraProvider) {
        val metrics = DisplayMetrics().also { binding.previewView.display.getRealMetrics(it) }
        val screenAspectRatio = aspectRatio(metrics.widthPixels, metrics.heightPixels)

        val rotation = binding.previewView.display.rotation

        val preview: Preview = Preview.Builder()
            .setTargetAspectRatio(screenAspectRatio)
            .setTargetRotation(rotation)
            .build()

        val cameraSelector: CameraSelector = CameraSelector.Builder()
            .requireLensFacing(CameraSelector.LENS_FACING_BACK)
            .build()

        val analyzer = ImageAnalysis.Builder()
            .setTargetAspectRatio(screenAspectRatio)
            .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
            .build()

        cameraProvider.unbindAll()

        analyzer.setAnalyzer(cameraExecutor, LicensePlateAnalyser(this))

        cameraProvider.bindToLifecycle(this as LifecycleOwner, cameraSelector, analyzer, preview)
        preview.setSurfaceProvider(binding.previewView.surfaceProvider)
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
    }

    override fun onLicensePlateRecognized(licensePlateNumber: String) {
        Timber.d("License plate recognized with number: $licensePlateNumber")

        val data = Intent().apply {
            putExtra(EXTRA_LICENSE_PLATE_RESULT, licensePlateNumber)
        }

        setResult(RESULT_OK, data)
        finish()
    }

    /**
     *  [androidx.camera.core.ImageAnalysisConfig] requires enum value of
     *  [androidx.camera.core.AspectRatio]. Currently it has values of 4:3 & 16:9.
     *
     *  Detecting the most suitable ratio for dimensions provided in @params by counting absolute
     *  of preview ratio to one of the provided values.
     *
     *  @param width - preview width
     *  @param height - preview height
     *  @return suitable aspect ratio
     */
    private fun aspectRatio(width: Int, height: Int): Int {
        val previewRatio = max(width, height).toDouble() / min(width, height)
        if (abs(previewRatio - AspectRatio.RATIO_4_3) <= abs(previewRatio - AspectRatio.RATIO_16_9)) {
            return AspectRatio.RATIO_4_3
        }
        return AspectRatio.RATIO_16_9
    }

}
