package nl.dennisvanderzalm.parking.shared.core.repository

import nl.dennisvanderzalm.parking.shared.core.model.AddressBookItem

interface AddressBookRepository {

    suspend fun getAddressBook(): List<AddressBookItem>
}
