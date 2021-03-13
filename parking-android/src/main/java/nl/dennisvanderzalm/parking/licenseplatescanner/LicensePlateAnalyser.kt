package nl.dennisvanderzalm.parking.licenseplatescanner

import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.Text
import com.google.mlkit.vision.text.TextRecognition
import timber.log.Timber

class LicensePlateAnalyser(private val listener: LicencePlateResultListener? = null) : ImageAnalysis.Analyzer {

    private val listeners = mutableListOf<LicencePlateResultListener>().apply { listener?.let { add(it) } }


    private val licensePlatePattern =
        Regex("^([a-zA-Z]{2})([0-9]{2})([0-9]{2})|([0-9]{2})([0-9]{2})([a-zA-Z]{2})|([0-9]{2})([a-zA-Z]{2})([0-9]{2})|([bdfghjklmnprstvwxyzBDFGHJKLMNPRSTVWXYZ]{2})([0-9]{2})([bdfghjklmnprstvwxyzBDFGHJKLMNPRSTVWXYZ]{2})|([bdfghjklmnprstvwxyzBDFGHJKLMNPRSTVWXYZ]{2})([bdfghjklmnprstvwxyzBDFGHJKLMNPRSTVWXYZ]{2})([0-9]{2})|([0-9]{2})([bdfghjklmnprstvwxyzBDFGHJKLMNPRSTVWXYZ]{2})([bdfghjklmnprstvwxyzBDFGHJKLMNPRSTVWXYZ]{2})|([0-9]{2})([bdfghjklmnprstvwxyzBDFGHJKLMNPRSTVWXYZ]{3})([0-9]{1})|([0-9]{1})([bdfghjklmnprstvwxyzBDFGHJKLMNPRSTVWXYZ]{3})([0-9]{2})|([bdfghjklmnprstvwxyzBDFGHJKLMNPRSTVWXYZ]{2})([0-9]{3})([bdfghjklmnprstvwxyzBDFGHJKLMNPRSTVWXYZ]{1})|([bdfghjklmnprstvwxyzBDFGHJKLMNPRSTVWXYZ]{1})([0-9]{3})([bdfghjklmnprstvwxyzBDFGHJKLMNPRSTVWXYZ]{2})|([bdfghjklmnprstvwxyzBDFGHJKLMNPRSTVWXYZ]{3})([0-9]{2})([bdfghjklmnprstvwxyzBDFGHJKLMNPRSTVWXYZ]{1})\$")

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
                if (licensePlatePattern.matches(lineText.replace("-", ""))) {
                    listeners.forEach { it.onLicensePlateRecognized(lineText) }
                } else {
                    Timber.d("Processes image, no numberplate recognized")
                }
            }
        }
    }

    interface LicencePlateResultListener {

        fun onLicensePlateRecognized(licensePlateNumber: String)
    }
}


