package nl.dennisvanderzalm.parking

import nl.dennisvanderzalm.parking.core.model.Config
import nl.dennisvanderzalm.parking.core.model.DataSourceConfig
import nl.dennisvanderzalm.parking.core.repository.AddressBookRepository
import nl.dennisvanderzalm.parking.core.repository.GuestParkingRepository
import nl.dennisvanderzalm.parking.core.repository.SessionRepository
import nl.dennisvanderzalm.parking.core.usecase.*
import nl.dennisvanderzalm.parking.data.auth.TokenProvider
import nl.dennisvanderzalm.parking.data.repository.AppAddressBookRepository
import nl.dennisvanderzalm.parking.data.repository.AppSessionRepository
import nl.dennisvanderzalm.parking.data.repository.GuestParkingRepositoryImpl
import nl.dennisvanderzalm.parking.data.service.GuestParkingService
import nl.dennisvanderzalm.parking.data.service.LoginService
import nl.dennisvanderzalm.parking.data.session.SessionManager
import nl.dennisvanderzalm.parking.data.source.GuestParkingDataSource
import nl.dennisvanderzalm.parking.data.source.LoginDataSource
import nl.dennisvanderzalm.parking.data.source.PaidParkingDataSource
import nl.dennisvanderzalm.parking.data.source.local.LocalPaidParkingDataSource
import nl.dennisvanderzalm.parking.data.source.remote.RemoteGuestParkingDataSource
import nl.dennisvanderzalm.parking.data.source.remote.RemoteLoginDataSource
import nl.dennisvanderzalm.parking.data.storage.MemoryStorage
import nl.dennisvanderzalm.parking.ui.create.CreateParkingReservationViewModel
import nl.dennisvanderzalm.parking.ui.login.LoginViewModel
import nl.dennisvanderzalm.parking.ui.parkingoverview.ParkingOverviewViewModel
import org.koin.core.KoinApplication
import org.koin.dsl.module
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.KoinAppDeclaration

expect val platformModule: Module

fun initKoin(config: Config, appDeclaration: KoinAppDeclaration): KoinApplication = startKoin {
    appDeclaration()

    modules(platformModule)
    modules(repositoryModule)
    modules(useCaseModule)
    modules(storageModule)
    modules(authModule)
    modules(localDataSourceModule)
    modules(viewModelModule)

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
    single<GuestParkingRepository> { GuestParkingRepositoryImpl(get(), get()) }
    single<SessionRepository> { AppSessionRepository(get(), get()) }
    single<AddressBookRepository> { AppAddressBookRepository(get()) }
}

private val authModule = module {
    single { SessionManager(get()) }
    single { TokenProvider(get()) }
}

private val storageModule = module {
    single { MemoryStorage() }
}

private val remoteDataSourceModule = module {
    single<LoginDataSource> { RemoteLoginDataSource(get()) }
    single<GuestParkingDataSource> { RemoteGuestParkingDataSource(get()) }
}

private val localDataSourceModule = module {
    single<PaidParkingDataSource> { LocalPaidParkingDataSource() }
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
    single { ResolveParkingReservationUseCase(get()) }
    single { GetStartupActionUseCase(get()) }
    single { GetAddressBookUseCase(get()) }
    single { LogoutUseCase(get()) }
    single { RefreshTokenUseCase(get()) }
}

private val viewModelModule = module {
    viewModelOf(::LoginViewModel)
    viewModelOf(::CreateParkingReservationViewModel)
    viewModelOf(::ParkingOverviewViewModel)
}