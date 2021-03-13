package nl.dennisvanderzalm.parking.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import nl.dennisvanderzalm.parking.shared.core.usecases.LoginUseCase
import timber.log.Timber

class LoginViewModel(private val loginUseCase: LoginUseCase) : ViewModel() {

    fun login(username: String, password: String) {
        viewModelScope.launch {
            try{
                loginUseCase.get(LoginUseCase.RequestValues(username, password))
            } catch (throwable: Throwable){
                Timber.e(throwable, "Unable to login")
            }
        }
    }
}