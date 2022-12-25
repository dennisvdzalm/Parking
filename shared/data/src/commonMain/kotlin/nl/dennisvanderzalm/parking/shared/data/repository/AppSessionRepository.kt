package nl.dennisvanderzalm.parking.shared.data.repository

import nl.dennisvanderzalm.parking.shared.core.repository.SessionRepository
import nl.dennisvanderzalm.parking.shared.data.session.SessionManager
import nl.dennisvanderzalm.parking.shared.data.source.LoginDataSource

class AppSessionRepository(
    private val sessionManager: SessionManager,
    private val loginDataSource: LoginDataSource,
) : SessionRepository {
    override val isSessionActive: Boolean get() = sessionManager.isSessionActive
    override suspend fun login(username: CharSequence, password: CharSequence) {
        val loginResponse = loginDataSource.login(username, password)
        sessionManager.userName = username.toString()
        sessionManager.password = password.toString()
        sessionManager.token = loginResponse.token
        sessionManager.permitCode = loginResponse.permits.first().permitMedias.first().code
    }

    override suspend fun refreshToken() {
        val username = requireNotNull(sessionManager.userName) {
            "Username cannot be null"
        }

        val password = requireNotNull(sessionManager.password) {
            "Password cannot be null"
        }

        login(username, password)
    }

    override suspend fun logout() {
        sessionManager.clear()
    }
}
