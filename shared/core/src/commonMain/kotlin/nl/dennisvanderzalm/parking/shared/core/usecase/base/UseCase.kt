package nl.dennisvanderzalm.parking.shared.core.usecase.base

interface UseCase<RV : UseCase.RequestValues, T> {

    suspend fun get(requestValues: RV): T

    interface RequestValues
}

