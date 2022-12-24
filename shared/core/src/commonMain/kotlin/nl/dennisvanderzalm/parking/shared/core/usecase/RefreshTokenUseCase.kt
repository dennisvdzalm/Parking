package nl.dennisvanderzalm.parking.shared.core.usecase

import nl.dennisvanderzalm.parking.shared.core.repository.SessionRepository
import nl.dennisvanderzalm.parking.shared.core.usecase.base.CompletableUseCase
import nl.dennisvanderzalm.parking.shared.core.usecase.base.UseCase

class RefreshTokenUseCase constructor(
    private val sessionRepository: SessionRepository
) : CompletableUseCase<RefreshTokenUseCase.RequestValues> {

    override suspend fun get(requestValues: RequestValues) {
        sessionRepository.refreshToken()
    }

    object RequestValues : UseCase.RequestValues
}
