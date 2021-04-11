package nl.dennisvanderzalm.parking.shared.data.repository

import nl.dennisvanderzalm.parking.shared.core.repositories.LoginRepository
import nl.dennisvanderzalm.parking.shared.data.session.SessionManager
import nl.dennisvanderzalm.parking.shared.data.source.LoginDataSource
import nl.dennisvanderzalm.parking.shared.data.storage.LocalStorage

class LoginRepositoryImpl(
    private val loginDataSource: LoginDataSource,
    private val sessionManager: SessionManager,
    private val storage: LocalStorage
) : LoginRepository {

    override suspend fun login(username: CharSequence, password: CharSequence) {
        val loginResponse = loginDataSource.login(username, password)
        sessionManager.token = loginResponse.token
        storage.storeOrUpdate("permits", loginResponse.permits)
    }
}
