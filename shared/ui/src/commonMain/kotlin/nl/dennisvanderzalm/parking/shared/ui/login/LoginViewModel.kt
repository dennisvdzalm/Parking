package nl.dennisvanderzalm.parking.shared.ui.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import nl.dennisvanderzalm.parking.shared.core.usecase.LoginUseCase

class LoginViewModel(
    private val scope: CoroutineScope,
    private val loginUseCase: LoginUseCase
) {

    var state by mutableStateOf<LoginViewState>(LoginViewState.EnterCredentials)
        private set

    fun login(username: String, password: String) {
        scope.launch {
            flow {
                emit(LoginViewState.Loading)
                loginUseCase(username, password)
                emit(LoginViewState.LoginSuccessFull)
            }.catch { throwable ->
                //Timber.e(throwable, "Unable to login")
                emit(LoginViewState.LoginError("Username or password is incorrect"))
            }.flowOn(Dispatchers.Default).collect { state = it }
        }
    }
}

sealed class LoginViewState {

    object EnterCredentials : LoginViewState()

    object Loading : LoginViewState()

    object LoginSuccessFull : LoginViewState()

    data class LoginError(val error: String) : LoginViewState()
}
