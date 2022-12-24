package nl.dennisvanderzalm.parking.shared.core.repository

interface SessionRepository {

    fun isSessionActive(): Boolean

    fun refreshToken()
}
