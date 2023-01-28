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
    
    @StateObject var viewModel: ParkingHistoryViewModel
    
    init (){
        let helper = UseCaseHelper()
        _viewModel = StateObject(
            wrappedValue: ParkingHistoryViewModel(
                getParkingHistoryUseCase: helper.getParkingHistoryUseCase,
                loginUseCase: helper.loginUseCase
            )
        )
        
    }
    var body: some View {
        NavigationView {
            List(viewModel.history, id: \.reservationId) { item in
                       PersonView(viewModel: viewModel, historyItem: item)
               }
               .navigationBarTitle(Text("Parking history"))
               .navigationBarTitleDisplayMode(.inline)
               .task {
                   await viewModel.getParkingHistory()
               }
           }
    }
}

struct PersonView: View {
    var viewModel: ParkingHistoryViewModel
    var historyItem: ParkingHistoryItem
    
    var body: some View {
        HStack {
            VStack(alignment: .leading) {
                Text(historyItem.licensePlate?.prettyNumber ?? "").font(.headline)
                Text(historyItem.validFrom.toNSDate().formatted()).font(.subheadline)
            }
        }
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
