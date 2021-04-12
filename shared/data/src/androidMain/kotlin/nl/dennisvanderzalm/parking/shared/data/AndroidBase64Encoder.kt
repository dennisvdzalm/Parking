package nl.dennisvanderzalm.parking.shared.data

import android.util.Base64
import nl.dennisvanderzalm.parking.shared.data.util.Base64Encoder

class AndroidBase64Encoder : Base64Encoder {

    override fun encode(value: String): String = Base64.encodeToString(value.toByteArray(), Base64.NO_WRAP)

    override fun decode(value: String): String = String(Base64.decode(value.toByteArray(), Base64.NO_WRAP))
}
