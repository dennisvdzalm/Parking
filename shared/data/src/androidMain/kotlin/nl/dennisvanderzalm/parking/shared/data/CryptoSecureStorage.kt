package nl.dennisvanderzalm.parking.shared.data

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import nl.dennisvanderzalm.parking.shared.data.util.SecurePreferences

private const val sharedPrefsFile: String = "PARKING_PREFS"

class CryptoSecureStorage(applicationContext: Context) : SecurePreferences {

    private val mainKey = MasterKey.Builder(applicationContext)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    private val sharedPreferences: SharedPreferences = EncryptedSharedPreferences.create(
        applicationContext,
        sharedPrefsFile,
        mainKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    override fun getString(key: String, default: String?): String? = sharedPreferences.getString(key, default)

    override fun putString(key: String, value: String) = sharedPreferences.edit().putString(key, value).apply()

    override fun getBoolean(key: String, default: Boolean): Boolean = sharedPreferences.getBoolean(key, default)

    override fun putBoolean(key: String, value: Boolean) = sharedPreferences.edit().putBoolean(key, value).apply()

    override fun remove(key: String) = sharedPreferences.edit().remove(key).apply()

    override fun contains(key: String): Boolean = sharedPreferences.contains(key)

    override fun clear() = sharedPreferences.edit().clear().apply()
}
