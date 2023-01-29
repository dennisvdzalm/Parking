//
//  ContentView.swift
//  parking
//
//  Created by Dennis van der Zalm on 28/01/2023.
//

import SwiftUI
import Combine
import shared

struct ContentView: View {

    @StateObject var viewModel: AppStateViewModel

    init() {
        let helper = UseCaseHelper()
        _viewModel = StateObject(
                wrappedValue: AppStateViewModel(
                        useCase: helper.startupActionUseCase
                )
        )
    }

    @ViewBuilder
    var body: some View {
        switch viewModel.launchAction {
        case .showoverview:
            History().environmentObject(viewModel)
        case .showlogin:
            Login().environmentObject(viewModel)
        default:
            Login().environmentObject(viewModel)
        }
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}


