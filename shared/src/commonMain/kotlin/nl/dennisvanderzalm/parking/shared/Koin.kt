package nl.dennisvanderzalm.parking.shared

import nl.dennisvanderzalm.parking.shared.core.models.Config
import nl.dennisvanderzalm.parking.shared.core.models.DataSourceConfig
import nl.dennisvanderzalm.parking.shared.core.repositories.GuestParkingRepository
import nl.dennisvanderzalm.parking.shared.core.repositories.LoginRepository
import nl.dennisvanderzalm.parking.shared.core.repositories.SessionRepository
import nl.dennisvanderzalm.parking.shared.core.usecases.*
import nl.dennisvanderzalm.parking.shared.data.auth.TokenProvider
import nl.dennisvanderzalm.parking.shared.data.repository.AppSessionRepository
import nl.dennisvanderzalm.parking.shared.data.repository.GuestParkingRepositoryImpl
import nl.dennisvanderzalm.parking.shared.data.repository.LoginRepositoryImpl
import nl.dennisvanderzalm.parking.shared.data.service.GuestParkingService
import nl.dennisvanderzalm.parking.shared.data.service.LoginService
import nl.dennisvanderzalm.parking.shared.data.session.SessionManager
import nl.dennisvanderzalm.parking.shared.data.source.GuestParkingDataSource
import nl.dennisvanderzalm.parking.shared.data.source.LoginDataSource
import nl.dennisvanderzalm.parking.shared.data.source.remote.RemoteGuestParkingDataSource
import nl.dennisvanderzalm.parking.shared.data.source.remote.RemoteLoginDataSource
import nl.dennisvanderzalm.parking.shared.data.storage.LocalStorage
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

expect val platformModule: Module

fun initKoin(config: Config, appDeclaration: KoinAppDeclaration = {}) = startKoin {
    appDeclaration()

    modules(platformModule)
    modules(repositoryModule)
    modules(useCaseModule)
    modules(storageModule)
    modules(authModule)

    when (val dataSourceConfig = config.dataSourceConfig) {
        is DataSourceConfig.Remote -> {
            modules(remoteDataSourceModule)
            modules(httpClientModule(dataSourceConfig))
        }
        DataSourceConfig.Fake -> {

        }
    }
}

// called by iOS etc
fun initKoin(config: Config) = initKoin(config) {}

private val repositoryModule = module {
    single<LoginRepository> { LoginRepositoryImpl(get(), get(), get()) }
    single<GuestParkingRepository> { GuestParkingRepositoryImpl(get(), get()) }
    single<SessionRepository> { AppSessionRepository(get()) }
}

private val authModule = module {
    single { SessionManager(get(), get()) }
    single { TokenProvider(get()) }
}

private val storageModule = module {
    single { LocalStorage() }
}

private val remoteDataSourceModule = module {
    single<LoginDataSource> { RemoteLoginDataSource(get()) }
    single<GuestParkingDataSource> { RemoteGuestParkingDataSource(get(), get()) }
}

private fun httpClientModule(config: DataSourceConfig.Remote) = module {
    single { LoginService(config) }
    single { GuestParkingService(config, get()) }
}

private val useCaseModule = module {
    single { LoginUseCase(get()) }
    single { GetParkingHistoryUseCase(get()) }
    single { CreateParkingReservationUseCase(get()) }
    single { EndParkingReservationUseCase(get()) }
    single { GetStartupActionUseCase(get()) }
}
