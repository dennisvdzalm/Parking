package nl.dennisvanderzalm.parking.shared.ui

import nl.dennisvanderzalm.parking.shared.core.usecase.CreateParkingReservationUseCase
import nl.dennisvanderzalm.parking.shared.core.usecase.EndParkingReservationUseCase
import nl.dennisvanderzalm.parking.shared.core.usecase.GetAddressBookUseCase
import nl.dennisvanderzalm.parking.shared.core.usecase.GetParkingHistoryUseCase
import nl.dennisvanderzalm.parking.shared.core.usecase.GetStartupActionUseCase
import nl.dennisvanderzalm.parking.shared.core.usecase.LoginUseCase
import nl.dennisvanderzalm.parking.shared.core.usecase.LogoutUseCase
import nl.dennisvanderzalm.parking.shared.core.usecase.ResolveParkingReservationUseCase
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

object Dependencies : KoinComponent {

    val getParkingHistoryUseCase: GetParkingHistoryUseCase by inject()
    val loginUseCase: LoginUseCase by inject()
    val startupActionUseCase: GetStartupActionUseCase by inject()
    val logoutUseCase: LogoutUseCase by inject()
    val getAddressBookUseCase: GetAddressBookUseCase by inject()
    val createParkingReservationUseCase: CreateParkingReservationUseCase by inject()
    val resolveCreateParkingReservationUseCase: ResolveParkingReservationUseCase by inject()
    val endParkingHistoryUseCase: EndParkingReservationUseCase by inject()
}