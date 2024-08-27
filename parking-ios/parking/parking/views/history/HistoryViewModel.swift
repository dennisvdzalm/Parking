//
// Created by Dennis van der Zalm on 28/01/2023.
//

import Foundation
import Combine
import shared

@MainActor
class HistoryViewModel: ObservableObject {
    @Published var history = [ParkingHistoryItem]()

    private let getParkingHistoryUseCase: GetParkingHistoryUseCase
    private let endParkingHistoryUseCase: EndParkingReservationUseCase
    private let logoutUseCase: LogoutUseCase

    init(
            getParkingHistoryUseCase: GetParkingHistoryUseCase,
            endParkingHistoryUseCase: EndParkingReservationUseCase,
            logoutUseCase: LogoutUseCase
    ) {
        self.getParkingHistoryUseCase = getParkingHistoryUseCase
        self.endParkingHistoryUseCase = endParkingHistoryUseCase
        self.logoutUseCase = logoutUseCase
    }

    func getParkingHistory() async {
        do {
            history = try await getParkingHistoryUseCase.invoke()
        } catch {
            print("Failed with error: \(error)")
        }
    }

    func endParkingReservation(reservationId: Int32) async {
        do {
            try await endParkingHistoryUseCase.invoke(reservationId: reservationId)
        } catch {
            print("Failed to stop transaction \(error)")
        }
    }

    func logout() async {
        do {
            try await logoutUseCase.invoke()
        } catch {
            print("Failed to stop transaction \(error)")
        }
    }
}
