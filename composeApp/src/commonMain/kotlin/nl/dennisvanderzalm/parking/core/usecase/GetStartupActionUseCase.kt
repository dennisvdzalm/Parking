package nl.dennisvanderzalm.parking.core.usecase

import nl.dennisvanderzalm.parking.core.model.StartupAction
import nl.dennisvanderzalm.parking.core.repository.SessionRepository

class GetStartupActionUseCase(private val sessionRepository: SessionRepository) {
    operator fun invoke(): StartupAction {
        return if (sessionRepository.isSessionActive) StartupAction.ShowOverview
        else StartupAction.ShowLogin
    }
}
