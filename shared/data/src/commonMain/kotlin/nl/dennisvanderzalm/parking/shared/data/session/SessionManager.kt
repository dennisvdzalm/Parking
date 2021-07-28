package nl.dennisvanderzalm.parking.shared.data.session

import nl.dennisvanderzalm.parking.shared.data.util.Base64Encoder
import nl.dennisvanderzalm.parking.shared.data.util.SecurePreferences

private const val TOKEN = "ACCESS_TOKEN"
private const val PERMIT_CODE = "PERMIT_CODE"

class SessionManager(
    private val securePreferences: SecurePreferences,
    private val base64Encoder: Base64Encoder
) {

    var token: String? = null
        get() = field ?: securePreferences.getString(TOKEN)?.also { field = it }
        set(value) {
            if (value != null) securePreferences.putString(TOKEN, base64Encoder.encode(value))
            else securePreferences.remove(TOKEN)
        }

    var permitCode: String? = null
        get() = field ?: securePreferences.getString(PERMIT_CODE)?.also { field = it }
        set(value) {
            if (value != null) securePreferences.putString(PERMIT_CODE, value)
            else securePreferences.remove(PERMIT_CODE)
        }

    val isSessionActive: Boolean = token != null

    fun clear() {
        token = null
        permitCode = null
    }
}
