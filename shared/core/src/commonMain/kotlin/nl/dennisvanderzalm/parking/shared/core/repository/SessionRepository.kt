package nl.dennisvanderzalm.parking.shared.core.repository

interface SessionRepository {

    val isSessionActive: Boolean
    suspend fun login(username: CharSequence, password: CharSequence)
    suspend fun refreshToken()
    suspend fun logout()
}
