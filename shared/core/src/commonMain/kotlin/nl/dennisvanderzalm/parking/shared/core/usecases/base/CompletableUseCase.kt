package nl.dennisvanderzalm.parking.shared.core.usecases.base

interface CompletableUseCase<RV : UseCase.RequestValues> : UseCase<RV, Unit>
