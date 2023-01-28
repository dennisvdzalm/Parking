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
    
    init(getParkingHistoryUseCase: GetParkingHistoryUseCase) {
        self.getParkingHistoryUseCase = getParkingHistoryUseCase
    }
    
    func getParkingHistory() async {
        do {
            history = try await getParkingHistoryUseCase.invoke()
        } catch {
            print("Failed with error: \(error)")
        }
    }
}
