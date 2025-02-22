package nl.dennisvanderzalm.parking.core.usecase

import nl.dennisvanderzalm.parking.core.repository.SessionRepository

class LogoutUseCase(private val loginRepository: SessionRepository) {

     suspend operator fun invoke() {
        loginRepository.logout()
    }
}