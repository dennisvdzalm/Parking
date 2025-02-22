package nl.dennisvanderzalm.parking

import nl.dennisvanderzalm.parking.data.util.SecurePreferences
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module = module {
    single<SecurePreferences> { CryptoSecurePreferences(get()) }
}
