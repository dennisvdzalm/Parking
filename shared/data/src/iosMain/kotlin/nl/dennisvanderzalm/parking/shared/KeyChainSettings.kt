package nl.dennisvanderzalm.parking.shared

import com.russhwolf.settings.*
import nl.dennisvanderzalm.parking.shared.data.util.SecurePreferences


@OptIn(ExperimentalSettingsImplementation::class)
class KeyChainSettings : SecurePreferences {
    private val settings: Settings = KeychainSettings(SecurePreferences.PREF_NAME)

    override fun getString(key: String, default: String?): String = settings.getString(key, default.orEmpty())

    override fun putString(key: String, value: String) = settings.putString(key, value)

    override fun getBoolean(key: String, default: Boolean): Boolean = settings.getBoolean(key, default)

    override fun putBoolean(key: String, value: Boolean) = settings.putBoolean(key, value)

    override fun remove(key: String) = settings.remove(key)

    override fun contains(key: String): Boolean = settings.contains(key)

    override fun clear() = settings.clear()
}