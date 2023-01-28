//
// Created by Dennis van der Zalm on 28/01/2023.
//

import Foundation
import shared

@MainActor
class CreateViewModel: ObservableObject {

    @Published var licensePlate = ""
    @Published var addressBook = [AddressBookItem]()
    @Published var respectPaidParkingHours = true
    @Published var parkingZone = ParkingZone.zonea
    @Published var start = Date.now
    @Published var end = Date.now


    private let addressBookUseCase: GetAddressBookUseCase
    private let createReservationUseCase: CreateParkingReservationUseCase
    private let resolveParkingReservationUseCase: ResolveParkingReservationUseCase

    init(
            addressBookUseCase: GetAddressBookUseCase,
            createReservationUseCase: CreateParkingReservationUseCase,
            resolveParkingReservationUseCase: ResolveParkingReservationUseCase
    ) {
        self.addressBookUseCase = addressBookUseCase
        self.createReservationUseCase = createReservationUseCase
        self.resolveParkingReservationUseCase = resolveParkingReservationUseCase

        validateFields()
    }

    private func validateFields() {
        $licensePlate
                // you ignore the first empty value that it gets initialised with
                .dropFirst()
                // you give the user a bit of time to finish typing
                .debounce(for: 0.6, scheduler: RunLoop.main)
                // you get rid of duplicated inputs as they do not change anything in terms of validation
                .removeDuplicates()
                // you validate the input string and in case of problems publish an error message
                .map { input in
                    if (DutchLicensePlateNumber.companion.tryParse(number: input)) {
                        return DutchLicensePlateNumber.companion.parse(number: input).prettyNumber
                    } else {
                        return input
                    }
                }
                // you publish the error message for the view to react to
                .assign(to: &$licensePlate)
    }

    func getAddressBook() async {
        do {
            addressBook = try await addressBookUseCase.invoke()
        } catch {
            print("Failed to fetch address book: \(error)")
        }
    }

    func createParkingReservation() async {
        do {
            let licensePlate = DutchLicensePlateNumber.companion.parse(number: self.licensePlate)
            let startInstant = Instant.companion.fromEpochMilliseconds(epochMilliseconds: Int64(start.timeIntervalSince1970) * 1000)
            let endInstant = Instant.companion.fromEpochMilliseconds(epochMilliseconds: Int64(end.timeIntervalSince1970) * 1000)
            let reservations = resolveParkingReservationUseCase.invoke(
                    respectPaidParkingHours: respectPaidParkingHours,
                    start: startInstant,
                    end: endInstant,
                    licensePlateNumber: licensePlate,
                    name: "",
                    zone: parkingZone
            )

            for reservation in reservations {
                try await createReservationUseCase.invoke(reservation: reservation)
            }
        } catch {
            print("Failed creating parking reservation: \(error)")
        }
    }
}