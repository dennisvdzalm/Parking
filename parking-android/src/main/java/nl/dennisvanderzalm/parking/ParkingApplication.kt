package nl.dennisvanderzalm.parking

import android.app.Application
import nl.dennisvanderzalm.parking.ui.login.LoginViewModel
import nl.dennisvanderzalm.parking.shared.di.Koin
import nl.dennisvanderzalm.parking.shared.core.models.Config
import nl.dennisvanderzalm.parking.shared.core.models.DataSourceConfig
import nl.dennisvanderzalm.parking.ui.parkingoverview.ParkingOverviewViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import timber.log.Timber

class ParkingApplication : Application() {

    private val config = Config(
        DataSourceConfig.Remote("parkeren.leiden.nl/DVSWebAPI/api")
    )

    override fun onCreate() {
        super.onCreate()

        Koin().initKoin(config) {
            androidContext(this@ParkingApplication)
            modules(
                module {
                    viewModel { LoginViewModel(get()) }
                    viewModel { ParkingOverviewViewModel(get()) }
                }
            )
        }

        Timber.plant(Timber.DebugTree())
    }

}