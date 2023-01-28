//
//  LoginViewModel.swift
//  parking
//
//  Created by Dennis van der Zalm on 28/01/2023.
//

import Foundation
import shared

@MainActor
class LoginViewModel: ObservableObject {
    @Published var passcode = ""
    @Published var password = ""

    private let loginUseCase: LoginUseCase

    init(loginUseCase: LoginUseCase) {
        self.loginUseCase = loginUseCase
    }

    func login() async {
        do {
            try await loginUseCase.invoke(username: self.passcode, password: self.password)
        } catch {
            print("Error logging in: \(error)")
        }
    }
}
