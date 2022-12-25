package nl.dennisvanderzalm.parking.ui.login

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import nl.dennisvanderzalm.parking.shared.core.usecase.LoginUseCase
import timber.log.Timber

class LoginViewModel(
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    var state by mutableStateOf<LoginViewState>(LoginViewState.EnterCredentials)
        private set

    fun login(username: String, password: String) {
        viewModelScope.launch {
            flow {
                emit(LoginViewState.Loading)
                loginUseCase(username, password)
                emit(LoginViewState.LoginSuccessFull)
            }.catch { throwable ->
                Timber.e(throwable, "Unable to login")
                emit(LoginViewState.LoginError("Username or password is incorrect"))
            }.flowOn(Dispatchers.IO)
                .collect { state = it }
        }
    }
}

sealed class LoginViewState {

    object EnterCredentials : LoginViewState()

    object Loading : LoginViewState()

    object LoginSuccessFull : LoginViewState()

    data class LoginError(val error: String) : LoginViewState()
}
