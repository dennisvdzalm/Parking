//
//  ContentViewViewModel.swift
//  parking
//
//  Created by Dennis van der Zalm on 28/01/2023.
//

import Foundation
import shared

class ContentViewViewModel: ObservableObject {
 
    @Published var launchAction: StartupAction
    
    private let getStartUpActionUseCase: GetStartupActionUseCase
    
    init(useCase: GetStartupActionUseCase) {
        getStartUpActionUseCase = useCase
        launchAction = getStartUpActionUseCase.invoke()
    }

}
