package nl.dennisvanderzalm.parking.shared.core.usecase

import nl.dennisvanderzalm.parking.shared.core.model.AddressBookItem
import nl.dennisvanderzalm.parking.shared.core.repository.AddressBookRepository
import nl.dennisvanderzalm.parking.shared.core.usecase.base.UseCase

class GetAddressBookUseCase(private val addressBookRepository: AddressBookRepository) :
    UseCase<GetAddressBookUseCase.RequestValues, List<AddressBookItem>> {

    override suspend fun get(requestValues: RequestValues): List<AddressBookItem> =
        addressBookRepository.getAddressBook()

    object RequestValues : UseCase.RequestValues
}
