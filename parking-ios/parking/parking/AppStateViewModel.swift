//
//  AppStateViewModel.swift
//  parking
//
//  Created by Dennis van der Zalm on 28/01/2023.
//

import Foundation
import shared

@MainActor
class AppStateViewModel: ObservableObject {

    @Published var launchAction: StartupAction

    private let getStartUpActionUseCase: GetStartupActionUseCase

    init(useCase: GetStartupActionUseCase) {
        getStartUpActionUseCase = useCase
        launchAction = getStartUpActionUseCase.invoke()
    }

    func onLoggedIn() {
        launchAction = StartupAction.showoverview
    }

    func onLoggedOut() {
        launchAction = StartupAction.showlogin
    }
}
