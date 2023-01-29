//
// Created by Dennis van der Zalm on 28/01/2023.
//

import Foundation
import SwiftUI
import shared

struct Create: View {

    @StateObject var viewModel: CreateViewModel
    @Environment(\.presentationMode) var presentationMode

    init() {
        let helper = UseCaseHelper()
        _viewModel = StateObject(
                wrappedValue: CreateViewModel(
                        addressBookUseCase: helper.getAddressBookUseCase,
                        createReservationUseCase: helper.createParkingReservationUseCase,
                        resolveParkingReservationUseCase: helper.resolveCreateParkingReservationUseCase
                )
        )
    }

    var body: some View {
        NavigationView {
            VStack {
                TextField("License plate", text: $viewModel.licensePlate)
                        .padding()
                        .cornerRadius(5)

                Picker("Zone", selection: $viewModel.parkingZone) {
                    Text("Zone A").tag(ParkingZone.zonea)
                    Text("Zone B1").tag(ParkingZone.zoneb1)
                    Text("Zone B2").tag(ParkingZone.zoneb2)
                }

                Toggle("Respect paid parking hours", isOn: $viewModel.respectPaidParkingHours)
                        .padding()

                DatePicker("End", selection: $viewModel.end)
                        .padding()

                Button(action: {
                    Task {
                        await viewModel.createParkingReservation()
                        presentationMode.wrappedValue.dismiss()
                    }
                }) {
                    Text("Create")
                }.padding()
            }
        }
                .navigationBarTitle(Text("Create parking reservation"))
                .navigationBarTitleDisplayMode(.inline)
                .task {
                    await viewModel.getAddressBook()
                }
    }
}

struct Create_Previews: PreviewProvider {
    static var previews: some View {
        History()
    }
}
