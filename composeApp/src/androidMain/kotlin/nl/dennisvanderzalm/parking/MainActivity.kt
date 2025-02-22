package nl.dennisvanderzalm.parking

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import nl.dennisvanderzalm.parking.data.RefreshTokenWorker
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.workmanager.dsl.worker
import org.koin.androidx.workmanager.koin.workManagerFactory
import org.koin.dsl.module
import kotlin.time.Duration.Companion.days
import kotlin.time.toJavaDuration

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            App()
        }
    }
}


@Preview
@Composable
fun AppAndroidPreview() {
    App()
}