package nl.dennisvanderzalm.parking

import android.app.Application
import nl.dennisvanderzalm.parking.data.RefreshTokenWorker
import nl.dennisvanderzalm.parking.shared.core.model.Config
import nl.dennisvanderzalm.parking.shared.core.model.DataSourceConfig
import nl.dennisvanderzalm.parking.shared.initKoin
import nl.dennisvanderzalm.parking.ui.ParkingAppViewModel
import nl.dennisvanderzalm.parking.ui.create.CreateParkingReservationViewModel
import nl.dennisvanderzalm.parking.ui.login.LoginViewModel
import nl.dennisvanderzalm.parking.ui.parkingoverview.ParkingOverviewViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.workmanager.dsl.worker
import org.koin.dsl.module
import timber.log.Timber

class ParkingApplication : Application() {

    private val config = Config(
        DataSourceConfig.Remote("parkeren.leiden.nl/DVSWebAPI/api")
    )

    override fun onCreate() {
        super.onCreate()

        initKoin(config) {
            androidContext(this@ParkingApplication)
            modules(
                module {
                    viewModel { LoginViewModel(get()) }
                    viewModel { ParkingOverviewViewModel(get(), get()) }
                    viewModel { CreateParkingReservationViewModel(get(), get(), get()) }
                    viewModel { ParkingAppViewModel(get()) }

                    worker { RefreshTokenWorker(androidContext(), get(), get()) }
                }
            )
        }

        Timber.plant(Timber.DebugTree())
    }

}
