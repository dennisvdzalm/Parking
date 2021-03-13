package nl.dennisvanderzalm.parking.shared.network.source.remote

import nl.dennisvanderzalm.parking.shared.network.source.LoginDataSource
import nl.dennisvanderzalm.parking.shared.network.service.LoginService

class RemoteLoginDataSource(private val service: LoginService) : LoginDataSource {

    override suspend fun login(username: CharSequence, password: CharSequence) {
        service.login(username, password)
    }


}