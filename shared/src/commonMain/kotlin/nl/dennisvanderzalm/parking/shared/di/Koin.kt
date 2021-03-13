package nl.dennisvanderzalm.parking.shared.di

import nl.dennisvanderzalm.parking.shared.core.models.Config
import nl.dennisvanderzalm.parking.shared.core.models.DataSourceConfig
import nl.dennisvanderzalm.parking.shared.core.usecases.LoginUseCase
import nl.dennisvanderzalm.parking.shared.core.repositories.LoginRepository
import nl.dennisvanderzalm.parking.shared.network.repositories.LoginRepositoryImpl
import nl.dennisvanderzalm.parking.shared.network.service.LoginService
import nl.dennisvanderzalm.parking.shared.network.source.LoginDataSource
import nl.dennisvanderzalm.parking.shared.network.source.remote.RemoteLoginDataSource
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

class Koin {

    fun initKoin(config: Config, appDeclaration: KoinAppDeclaration = {}) = startKoin {
        appDeclaration()

        modules(repositoryModule)
        modules(useCaseModule)

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
        single<LoginRepository> { LoginRepositoryImpl(get()) }
    }

    private val remoteDataSourceModule = module {
        single<LoginDataSource> { RemoteLoginDataSource(get()) }
    }

    private fun httpClientModule(config: DataSourceConfig.Remote) = module {
        single { LoginService(config) }
    }

    private val useCaseModule = module {
        single { LoginUseCase(get()) }
    }
}