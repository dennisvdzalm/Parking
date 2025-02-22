package nl.dennisvanderzalm.parking.core.usecase

import nl.dennisvanderzalm.parking.core.repository.SessionRepository

class LoginUseCase constructor(
    private val loginRepository: SessionRepository
) {

    suspend operator fun invoke(username: String, password: String) {
        loginRepository.login(username, password)
    }
}
