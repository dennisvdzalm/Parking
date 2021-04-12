package nl.dennisvanderzalm.parking.shared.core.usecase

import nl.dennisvanderzalm.parking.shared.core.model.StartupAction
import nl.dennisvanderzalm.parking.shared.core.repository.SessionRepository
import nl.dennisvanderzalm.parking.shared.core.usecase.base.UseCase

class GetStartupActionUseCase(private val sessionRepository: SessionRepository) :
    UseCase<GetStartupActionUseCase.RequestValues, StartupAction> {

    override suspend fun get(requestValues: RequestValues): StartupAction {
        return if (sessionRepository.isSessionActive()) StartupAction.ShowOverview
        else StartupAction.ShowLogin
    }

    object RequestValues : UseCase.RequestValues
}
