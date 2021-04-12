package nl.dennisvanderzalm.parking.shared.data.repository

import nl.dennisvanderzalm.parking.shared.core.repository.LoginRepository
import nl.dennisvanderzalm.parking.shared.data.session.SessionManager
import nl.dennisvanderzalm.parking.shared.data.source.LoginDataSource

class LoginRepositoryImpl(
    private val loginDataSource: LoginDataSource,
    private val sessionManager: SessionManager
) : LoginRepository {

    override suspend fun login(username: CharSequence, password: CharSequence) {
        val loginResponse = loginDataSource.login(username, password)
        sessionManager.token = loginResponse.token
        sessionManager.permitCode = loginResponse.permits.first().permitMedias.first().code
    }
}
