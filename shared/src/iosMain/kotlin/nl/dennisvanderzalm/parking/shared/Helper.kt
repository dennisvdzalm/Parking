package nl.dennisvanderzalm.parking.shared

import nl.dennisvanderzalm.parking.shared.core.usecase.*
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class UseCaseHelper : KoinComponent {
    val getParkingHistoryUseCase: GetParkingHistoryUseCase by inject()
    val loginUseCase: LoginUseCase by inject()
    val startupActionUseCase: GetStartupActionUseCase by inject()
    val logoutUseCase: LogoutUseCase by inject()
    val getAddressBookUseCase: GetAddressBookUseCase by inject()
    val createParkingReservationUseCase: CreateParkingReservationUseCase by inject()
    val resolveCreateParkingReservationUseCase: ResolveParkingReservationUseCase by inject()
    val endParkingHistoryUseCase: EndParkingReservationUseCase by inject()
}