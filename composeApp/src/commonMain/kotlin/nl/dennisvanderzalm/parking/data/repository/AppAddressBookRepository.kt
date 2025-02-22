package nl.dennisvanderzalm.parking.data.repository

import nl.dennisvanderzalm.parking.core.model.AddressBookItem
import nl.dennisvanderzalm.parking.core.model.DutchLicensePlateNumber
import nl.dennisvanderzalm.parking.core.repository.AddressBookRepository
import nl.dennisvanderzalm.parking.data.service.GuestParkingService

class AppAddressBookRepository(private val service: GuestParkingService) : AddressBookRepository {

    override suspend fun getAddressBook(): List<AddressBookItem> = service.getAddressBook()
        .mapNotNull { dataModel ->
            dataModel.name?.let {
                AddressBookItem(it, DutchLicensePlateNumber.parse(dataModel.value))
            }
        }
}
