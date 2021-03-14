package nl.dennisvanderzalm.parking.shared.data.source.remote

import nl.dennisvanderzalm.parking.shared.data.source.LoginDataSource
import nl.dennisvanderzalm.parking.shared.data.service.LoginService

class RemoteLoginDataSource(private val service: LoginService) : LoginDataSource {

    override suspend fun login(username: CharSequence, password: CharSequence) {
        service.login(username, password)
    }


}