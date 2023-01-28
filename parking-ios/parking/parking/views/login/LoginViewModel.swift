//
//  LoginViewModel.swift
//  parking
//
//  Created by Dennis van der Zalm on 28/01/2023.
//

import Foundation
import shared

class LoginViewModel: ObservableObject {
    @Published var email = ""
    @Published var password = ""

    private let loginUseCase: LoginUseCase
    
    init(loginUseCase: LoginUseCase){
        self.loginUseCase = loginUseCase
    }
    
    func login() {
    
        Task { @MainActor in
            do {
                try await loginUseCase.invoke(username: self.email, password: self.password)
            } catch {
                print("Error logging in: \(error)")
            }
                }
        }
}
