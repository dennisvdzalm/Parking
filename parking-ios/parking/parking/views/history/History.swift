//
//  History.swift
//  parking
//
//  Created by Dennis van der Zalm on 28/01/2023.
//

import Foundation
import SwiftUI
import Combine
import shared

struct History: View {
    
    @StateObject var viewModel: HistoryViewModel
    
    init (){
        let helper = UseCaseHelper()
        _viewModel = StateObject(
            wrappedValue: HistoryViewModel(
                getParkingHistoryUseCase: helper.getParkingHistoryUseCase
            )
        )
        
    }
    
    var body: some View {
        NavigationView {
            List(viewModel.history, id: \.reservationId) {
                item in HistoryItem(viewModel: viewModel, historyItem: item)
            }
               .navigationBarTitle(Text("Parking history"))
               .navigationBarTitleDisplayMode(.inline)
               .task {
                   await viewModel.getParkingHistory()
               }
           }
    }
}

struct HistoryItem: View {
    var viewModel: HistoryViewModel
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

struct History_Previews: PreviewProvider {
    static var previews: some View {
        History()
    }
}
