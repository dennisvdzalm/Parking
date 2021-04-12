package nl.dennisvanderzalm.parking.shared.core.usecase

import nl.dennisvanderzalm.parking.shared.core.repository.LoginRepository
import nl.dennisvanderzalm.parking.shared.core.usecase.base.CompletableUseCase
import nl.dennisvanderzalm.parking.shared.core.usecase.base.UseCase

class LoginUseCase constructor(
    private val loginRepository: LoginRepository
) : CompletableUseCase<LoginUseCase.RequestValues> {

    override suspend fun get(requestValues: RequestValues) {
        loginRepository.login(requestValues.username, requestValues.password)
    }

    data class RequestValues(val username: String, val password: String) : UseCase.RequestValues
}
