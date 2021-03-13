package nl.dennisvanderzalm.parking.shared.core.usecases

import nl.dennisvanderzalm.parking.shared.core.repositories.LoginRepository
import nl.dennisvanderzalm.parking.shared.core.usecases.base.CompletableUseCase

class LoginUseCase constructor(
    private val loginRepository: LoginRepository
) : CompletableUseCase<LoginUseCase.RequestValues> {

    override suspend fun get(requestValues: RequestValues) {
        loginRepository.login(requestValues.username, requestValues.password)
    }

    data class RequestValues(val username: String, val password: String) : CompletableUseCase.RequestValues
}