//
// Created by Dennis van der Zalm on 28/01/2023.
//

import Foundation
import Combine
import shared

@MainActor
class ParkingHistoryViewModel: ObservableObject {
    @Published var history = [ParkingHistoryItem]()
    
    private let getParkingHistoryUseCase: GetParkingHistoryUseCase
    private let loginUseCase: LoginUseCase
    
    init(
        getParkingHistoryUseCase: GetParkingHistoryUseCase,
        loginUseCase: LoginUseCase
    ) {
        self.getParkingHistoryUseCase = getParkingHistoryUseCase
        self.loginUseCase = loginUseCase
    }
    
    func getParkingHistory() async {
        do {
            history = try await getParkingHistoryUseCase.invoke()
        } catch {
            print("Failed with error: \(error)")
        }
    }
}
