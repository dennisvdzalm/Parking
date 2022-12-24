package nl.dennisvanderzalm.parking.ui.util

import androidx.compose.runtime.Composable

sealed class ViewState<out T, out E> {

    data class Success<T>(val value: T) : ViewState<T, Nothing>()

    object Loading : ViewState<Nothing, Nothing>()

    data class Refreshing<T>(val previousValue: T) : ViewState<T, Nothing>()

    data class Error<E>(val error: E) : ViewState<Nothing, E>()

    /**
     * Convenience property that gets the value of the view state without needing to check its state.
     */
    val currentValue: T?
        get() = when (this) {
            is Success -> value
            is Loading -> null
            is Error -> null
            is Refreshing -> previousValue
        }

    /**
     * Convenience property that gets the error of the view state without needing to check its state.
     */
    val currentError: E?
        get() = when (this) {
            is Success -> null
            is Loading -> null
            is Error -> error
            is Refreshing -> null
        }

    val isRefreshing: Boolean
        get() = this is Refreshing

    val isNotSuccess = this !is Success
}


@Composable
fun <T, E> ViewState<T, E>.ToStateComposable(
    onError: @Composable (E) -> Unit = {},
    onLoading: @Composable () -> Unit = {},
    onSuccess: @Composable (data: T, isRefreshing: Boolean) -> Unit = { _, _ -> },
) {
    when (this) {
        is ViewState.Error -> onError(currentError ?: return)
        is ViewState.Loading -> onLoading()
        is ViewState.Refreshing, is ViewState.Success -> onSuccess(currentValue ?: return, isRefreshing)
    }
}
