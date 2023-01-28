package nl.dennisvanderzalm.parking.shared

import nl.dennisvanderzalm.parking.shared.core.usecase.GetParkingHistoryUseCase
import nl.dennisvanderzalm.parking.shared.core.usecase.GetStartupActionUseCase
import nl.dennisvanderzalm.parking.shared.core.usecase.LoginUseCase
import nl.dennisvanderzalm.parking.shared.core.usecase.LogoutUseCase
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class UseCaseHelper : KoinComponent {
    val getParkingHistoryUseCase: GetParkingHistoryUseCase by inject()
    val loginUseCase: LoginUseCase by inject()
    val startupActionUseCase: GetStartupActionUseCase by inject()
    val logoutUseCase: LogoutUseCase by inject()
}