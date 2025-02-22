package nl.dennisvanderzalm.parking.core.repository

import nl.dennisvanderzalm.parking.core.model.AddressBookItem

interface AddressBookRepository {

    suspend fun getAddressBook(): List<AddressBookItem>
}
