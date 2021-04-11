package nl.dennisvanderzalm.parking.shared.data.session

import nl.dennisvanderzalm.parking.shared.data.util.Base64Encoder
import nl.dennisvanderzalm.parking.shared.data.util.SecurePreferences

private const val TOKEN = "ACCESS_TOKEN"

class SessionManager(
    private val securePreferences: SecurePreferences,
    private val base64Encoder: Base64Encoder
) {

    private var _token: String? = null
    var token: String?
        get() = _token ?: securePreferences.getString(TOKEN)?.also { _token = it }
        set(value) {
            if (value != null) securePreferences.putString(TOKEN, base64Encoder.encode(value))
            else securePreferences.remove(TOKEN)
        }

    val isSessionActive: Boolean = token != null
}
