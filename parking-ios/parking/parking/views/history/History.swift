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

    @EnvironmentObject var appStateViewModel: AppStateViewModel
    @StateObject var viewModel: HistoryViewModel

    init() {
        let helper = UseCaseHelper()
        _viewModel = StateObject(
                wrappedValue: HistoryViewModel(
                        getParkingHistoryUseCase: helper.getParkingHistoryUseCase,
                        endParkingHistoryUseCase: helper.endParkingHistoryUseCase,
                        logoutUseCase: helper.logoutUseCase
                )
        )

    }

    var body: some View {
        NavigationView {
            List {
                ForEach(viewModel.history, id: \.reservationId) { historyItem in
                    HistoryItem(viewModel: viewModel, historyItem: historyItem)
                }
            }
                    .navigationBarTitle(Text("Parking history"))
                    .navigationBarTitleDisplayMode(.inline)
                    .navigationBarItems(
                            leading: Button(
                                    action: {
                                        Task {
                                            await viewModel.logout()
                                            appStateViewModel.onLoggedOut()
                                        }
                                    },
                                    label: { Text("Log out") }
                            ),
                            trailing: NavigationLink(destination: Create()) {
                                Image(systemName: "plus")
                            })
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
                .swipeActions(allowsFullSwipe: false) {
                    Button {
                        Task {
                            await viewModel.endParkingReservation(reservationId: historyItem.reservationId)
                        }
                    } label: {
                        Label("Stop", systemImage: "xmark.circle")
                    }
                            .tint(.red)
                }
    }
}

struct History_Previews: PreviewProvider {
    static var previews: some View {
        History()
    }
}
