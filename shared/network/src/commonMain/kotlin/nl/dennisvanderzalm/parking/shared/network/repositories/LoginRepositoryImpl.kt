package nl.dennisvanderzalm.parking.shared.network.repositories

import nl.dennisvanderzalm.parking.shared.core.repositories.LoginRepository
import nl.dennisvanderzalm.parking.shared.network.source.LoginDataSource

class LoginRepositoryImpl(private val loginDataSource: LoginDataSource) : LoginRepository {

    override suspend fun login(username: CharSequence, password: CharSequence) =
        loginDataSource.login(username, password)

}