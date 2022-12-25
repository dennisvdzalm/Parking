package nl.dennisvanderzalm.parking.data

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import nl.dennisvanderzalm.parking.ext.suspendRunCatching
import nl.dennisvanderzalm.parking.shared.core.usecase.RefreshTokenUseCase

class RefreshTokenWorker(
    appContext: Context,
    workerParams: WorkerParameters,
    private val refreshTokenUseCase: RefreshTokenUseCase,
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        suspendRunCatching { refreshTokenUseCase() }
        return Result.success()
    }
}