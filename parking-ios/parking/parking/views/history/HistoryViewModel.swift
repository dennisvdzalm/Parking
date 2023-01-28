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

    init(
            getParkingHistoryUseCase: GetParkingHistoryUseCase,
            endParkingHistoryUseCase: EndParkingReservationUseCase
    ) {
        self.getParkingHistoryUseCase = getParkingHistoryUseCase
        self.endParkingHistoryUseCase = endParkingHistoryUseCase
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
}
