package nl.dennisvanderzalm.parking.data

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import nl.dennisvanderzalm.parking.shared.core.usecase.GetParkingHistoryUseCase

class CheckActiveTransactionsWorker(
    appContext: Context,
    workerParams: WorkerParameters,
    private val getParkingHistoryUseCase: GetParkingHistoryUseCase
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        val history = getParkingHistoryUseCase()

        return Result.success()
    }
}
