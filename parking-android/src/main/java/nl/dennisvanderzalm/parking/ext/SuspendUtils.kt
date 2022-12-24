package nl.dennisvanderzalm.parking.ext

import timber.log.Timber
import java.util.concurrent.CancellationException

/**
 * Attempts [block], returning a successful [Result] if it succeeds, otherwise a [Result.Failure]
 * taking care not to break structured concurrency
 */
suspend fun <T> suspendRunCatching(block: suspend () -> T): Result<T> = try {
    Result.success(block())
} catch (cancellationException: CancellationException) {
    throw cancellationException
} catch (exception: Exception) {
    Timber.i(exception, "Failed to evaluate a suspendRunCatchingBlock. Returning failure Result")
    Result.failure(exception)
}