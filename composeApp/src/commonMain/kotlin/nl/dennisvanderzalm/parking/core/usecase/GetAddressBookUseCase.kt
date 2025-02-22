package nl.dennisvanderzalm.parking.core.usecase

import nl.dennisvanderzalm.parking.core.model.AddressBookItem
import nl.dennisvanderzalm.parking.core.repository.AddressBookRepository

class GetAddressBookUseCase(private val addressBookRepository: AddressBookRepository) {

    suspend operator fun invoke(): List<AddressBookItem> = addressBookRepository.getAddressBook()
}
