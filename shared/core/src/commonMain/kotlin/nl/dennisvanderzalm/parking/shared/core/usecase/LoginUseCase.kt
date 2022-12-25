package nl.dennisvanderzalm.parking.shared.core.usecase

import nl.dennisvanderzalm.parking.shared.core.repository.SessionRepository

class LoginUseCase constructor(
    private val loginRepository: SessionRepository
) {

    suspend operator fun invoke(username: String, password: String) {
        loginRepository.login(username, password)
    }
}
