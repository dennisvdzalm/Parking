package nl.dennisvanderzalm.parking.licenseplatescanner

import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.Text
import com.google.mlkit.vision.text.TextRecognition
import nl.dennisvanderzalm.parking.shared.core.models.isLicensePlate
import nl.dennisvanderzalm.parking.shared.core.models.toLicensePlateNumber
import timber.log.Timber

class LicensePlateAnalyser(private val listener: LicencePlateResultListener? = null) : ImageAnalysis.Analyzer {

    private val listeners = mutableListOf<LicencePlateResultListener>().apply { listener?.let { add(it) } }

    override fun analyze(imageProxy: ImageProxy) {
        if (listeners.isEmpty()) return

        val mediaImage = imageProxy.image
        if (mediaImage == null) return

        val image = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)

        TextRecognition
            .getClient()
            .process(image)
            .addOnSuccessListener {
                processNumberPlate(it)
                imageProxy.close()
            }
            .addOnFailureListener {
                Timber.e(it, "Failed to process image")
                imageProxy.close()
            }
    }


    private fun processNumberPlate(text: Text) {
        for (block in text.textBlocks) {
            for (line in block.lines) {
                val lineText = line.text

                Timber.d("Text: $lineText")
                if (lineText.isLicensePlate()) {
                    listeners.forEach { it.onLicensePlateRecognized(lineText.toLicensePlateNumber().prettyNumber) }
                } else {
                    Timber.d("Processed image, no numberplate recognized")
                }
            }
        }
    }

    interface LicencePlateResultListener {

        fun onLicensePlateRecognized(licensePlateNumber: String)
    }
}


