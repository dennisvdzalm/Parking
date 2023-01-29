//
//  Login.swift
//  parking
//
//  Created by Dennis van der Zalm on 28/01/2023.
//

import SwiftUI
import shared

struct Login: View {
    @EnvironmentObject var appStateViewModel: AppStateViewModel
    @StateObject var viewModel: LoginViewModel

    init() {
        let helper = UseCaseHelper()
        _viewModel = StateObject(
                wrappedValue: LoginViewModel(loginUseCase: helper.loginUseCase)
        )
    }

    var body: some View {
        VStack {
            TextField("Passcode", text: $viewModel.passcode)
                    .cornerRadius(5)
                    .padding()

            SecureField("Password", text: $viewModel.password)
                    .cornerRadius(5)
                    .padding()

            Button(action: {
                Task {
                    await viewModel.login()
                    appStateViewModel.onLoggedIn()
                }
            }) {
                Text("Login")
            }.padding()
        }
    }
}

struct Login_Previews: PreviewProvider {
    static var previews: some View {
        Login()
    }
}
