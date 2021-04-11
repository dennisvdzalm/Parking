package nl.dennisvanderzalm.parking.shared

import nl.dennisvanderzalm.parking.shared.data.AndroidBase64Encoder
import nl.dennisvanderzalm.parking.shared.data.CryptoSecureStorage
import nl.dennisvanderzalm.parking.shared.data.util.Base64Encoder
import nl.dennisvanderzalm.parking.shared.data.util.SecurePreferences
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module = module {
    single<SecurePreferences> { CryptoSecureStorage(get()) }
    single<Base64Encoder> { AndroidBase64Encoder() }
}
