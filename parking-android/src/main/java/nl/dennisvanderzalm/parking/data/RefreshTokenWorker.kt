package nl.dennisvanderzalm.parking.data

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import nl.dennisvanderzalm.parking.shared.core.ext.suspendRunCatching
import nl.dennisvanderzalm.parking.shared.core.usecase.RefreshTokenUseCase
import timber.log.Timber

class RefreshTokenWorker(
    appContext: Context,
    workerParams: WorkerParameters,
    private val refreshTokenUseCase: RefreshTokenUseCase,
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        Timber.d("Refreshing token")
        suspendRunCatching { refreshTokenUseCase() }
        return Result.success()
    }
}