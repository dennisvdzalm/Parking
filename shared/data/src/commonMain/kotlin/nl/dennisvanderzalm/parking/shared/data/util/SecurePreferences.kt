package nl.dennisvanderzalm.parking.shared.data.util

interface SecurePreferences {

    fun getString(key: String, default: String? = null): String?
    fun putString(key: String, value: String)
    fun getBoolean(key: String, default: Boolean): Boolean?
    fun putBoolean(key: String, value: Boolean)
    fun remove(key: String)
    fun contains(key: String): Boolean
    fun clear()
}
