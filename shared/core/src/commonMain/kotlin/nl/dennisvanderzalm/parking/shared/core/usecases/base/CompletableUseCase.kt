package nl.dennisvanderzalm.parking.shared.core.usecases.base

interface CompletableUseCase<RV : CompletableUseCase.RequestValues>{

    suspend fun get(requestValues: RV)

    interface RequestValues
}