package nl.dennisvanderzalm.parking.shared.core.usecase.base

interface CompletableUseCase<RV : UseCase.RequestValues> : UseCase<RV, Unit>
