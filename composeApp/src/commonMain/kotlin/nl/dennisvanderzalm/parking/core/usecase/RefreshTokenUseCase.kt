package nl.dennisvanderzalm.parking.core.usecase

import nl.dennisvanderzalm.parking.core.repository.SessionRepository

class RefreshTokenUseCase constructor(
    private val sessionRepository: SessionRepository
) {
    suspend operator fun invoke() {
        sessionRepository.refreshToken()
    }
}
