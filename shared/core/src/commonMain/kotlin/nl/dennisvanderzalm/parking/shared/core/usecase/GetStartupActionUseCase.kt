package nl.dennisvanderzalm.parking.shared.core.usecase

import nl.dennisvanderzalm.parking.shared.core.model.StartupAction
import nl.dennisvanderzalm.parking.shared.core.repository.SessionRepository

class GetStartupActionUseCase(private val sessionRepository: SessionRepository) {
    operator fun invoke(): StartupAction {
        return if (sessionRepository.isSessionActive) StartupAction.ShowOverview
        else StartupAction.ShowLogin
    }
}
