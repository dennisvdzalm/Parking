//
//  Login.swift
//  parking
//
//  Created by Dennis van der Zalm on 28/01/2023.
//

import SwiftUI
import shared

struct Login: View {
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
                    .padding()
                    .background(Color.white)
                    .cornerRadius(5)

            SecureField("Password", text: $viewModel.password)
                    .padding()
                    .background(Color.white)
                    .cornerRadius(5)

            Button(action: {
                Task {
                    await viewModel.login()
                }
            }) {
                Text("Login")
            }
        }
    }
}

struct Login_Previews: PreviewProvider {
    static var previews: some View {
        Login()
    }
}
