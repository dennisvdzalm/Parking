package nl.dennisvanderzalm.parking.shared

import nl.dennisvanderzalm.parking.shared.data.util.SecurePreferences
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module = module {
    single<SecurePreferences> { KeyChainSettings() }
}
