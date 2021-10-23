package nl.dennisvanderzalm.parking.shared.data.session

import nl.dennisvanderzalm.parking.shared.data.util.Base64
import nl.dennisvanderzalm.parking.shared.data.util.SecurePreferences
import nl.dennisvanderzalm.parking.shared.data.util.encodeToString

private const val TOKEN = "ACCESS_TOKEN"
private const val PERMIT_CODE = "PERMIT_CODE"

class SessionManager(private val securePreferences: SecurePreferences) {

    var token: String? = null
        get() = field ?: securePreferences.getString(TOKEN)?.also { field = it }
        set(value) {
            if (value != null) securePreferences.putString(TOKEN, Base64.encodeToString(value))
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
