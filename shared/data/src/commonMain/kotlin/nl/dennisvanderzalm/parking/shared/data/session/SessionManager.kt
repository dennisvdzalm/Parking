package nl.dennisvanderzalm.parking.shared.data.session

import nl.dennisvanderzalm.parking.shared.data.util.Base64
import nl.dennisvanderzalm.parking.shared.data.util.SecurePreferences
import nl.dennisvanderzalm.parking.shared.data.util.encodeToString

private const val USERNAME = "USERNAME"
private const val PASSWORD = "PASSWORD"
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

    var userName: String? = null
        get() = field ?: securePreferences.getString(USERNAME)?.also { field = it }
        set(value) {
            if (value != null) securePreferences.putString(USERNAME, value)
            else securePreferences.remove(USERNAME)
        }

    var password: String? = null
        get() = field ?: securePreferences.getString(PASSWORD)?.also { field = it }
        set(value) {
            if (value != null) securePreferences.putString(PASSWORD, value)
            else securePreferences.remove(PASSWORD)
        }

    val isSessionActive: Boolean = !token.isNullOrEmpty()

    fun clear() {
        token = null
        permitCode = null
        userName = null
        password = null
    }
}
