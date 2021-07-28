package nl.dennisvanderzalm.parking.shared.data.repository

import nl.dennisvanderzalm.parking.shared.core.model.AddressBookItem
import nl.dennisvanderzalm.parking.shared.core.model.DutchLicensePlateNumber
import nl.dennisvanderzalm.parking.shared.core.repository.AddressBookRepository
import nl.dennisvanderzalm.parking.shared.data.service.GuestParkingService

class AppAddressBookRepository(private val service: GuestParkingService) : AddressBookRepository {

    override suspend fun getAddressBook(): List<AddressBookItem> = service.getAddressBook()
        .mapNotNull { dataModel ->
            dataModel.name?.let {
                AddressBookItem(it, DutchLicensePlateNumber.parse(dataModel.value))
            }
        }
}
