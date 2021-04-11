package nl.dennisvanderzalm.parking.shared.core.usecases

import nl.dennisvanderzalm.parking.shared.core.models.StartupAction
import nl.dennisvanderzalm.parking.shared.core.repositories.SessionRepository
import nl.dennisvanderzalm.parking.shared.core.usecases.base.UseCase

class GetStartupActionUseCase(private val sessionRepository: SessionRepository) :
    UseCase<GetStartupActionUseCase.RequestValues, StartupAction> {

    override suspend fun get(requestValues: RequestValues): StartupAction {
        return if (sessionRepository.isSessionActive()) StartupAction.ShowOverview
        else StartupAction.ShowLogin
    }

    object RequestValues : UseCase.RequestValues
}
