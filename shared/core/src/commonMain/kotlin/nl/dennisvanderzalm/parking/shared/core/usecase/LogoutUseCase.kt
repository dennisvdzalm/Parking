package nl.dennisvanderzalm.parking.shared.core.usecase

import nl.dennisvanderzalm.parking.shared.core.repository.SessionRepository

class LogoutUseCase(private val loginRepository: SessionRepository) {

     suspend operator fun invoke() {
        loginRepository.logout()
    }
}