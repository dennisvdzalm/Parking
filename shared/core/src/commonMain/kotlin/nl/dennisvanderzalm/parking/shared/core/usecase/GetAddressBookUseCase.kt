package nl.dennisvanderzalm.parking.shared.core.usecase

import nl.dennisvanderzalm.parking.shared.core.model.AddressBookItem
import nl.dennisvanderzalm.parking.shared.core.repository.AddressBookRepository

class GetAddressBookUseCase(private val addressBookRepository: AddressBookRepository) {

    suspend operator fun invoke(): List<AddressBookItem> = addressBookRepository.getAddressBook()
}
