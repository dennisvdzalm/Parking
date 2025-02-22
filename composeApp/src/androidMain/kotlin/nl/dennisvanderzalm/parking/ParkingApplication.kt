package nl.dennisvanderzalm.parking

import android.app.Application
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import nl.dennisvanderzalm.parking.core.model.Config
import nl.dennisvanderzalm.parking.core.model.DataSourceConfig
import nl.dennisvanderzalm.parking.data.RefreshTokenWorker
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.workmanager.dsl.worker
import org.koin.androidx.workmanager.koin.workManagerFactory
import org.koin.dsl.module
import timber.log.Timber
import kotlin.time.Duration.Companion.days
import kotlin.time.toJavaDuration

class ParkingApplication : Application() {

    private val config = Config(
        DataSourceConfig.Remote("parkeren.leiden.nl")
    )

    override fun onCreate() {
        super.onCreate()

        initKoin(config) {
            androidContext(this@ParkingApplication)
            workManagerFactory()
            modules(
                module {
                    worker { RefreshTokenWorker(androidContext(), get(), get()) }
                }
            )
        }

        Timber.plant(Timber.DebugTree())

        setupRefreshTokenWorker()
    }

    private fun setupRefreshTokenWorker() {
        val workRequest =
            PeriodicWorkRequestBuilder<RefreshTokenWorker>(2.days.toJavaDuration()).build()
        WorkManager.getInstance(this).enqueue(workRequest)
    }
}
