package nl.dennisvanderzalm.parking.data.source.remote

import nl.dennisvanderzalm.parking.data.model.login.LoginResponseDataModel
import nl.dennisvanderzalm.parking.data.source.LoginDataSource
import nl.dennisvanderzalm.parking.data.service.LoginService

class RemoteLoginDataSource(private val service: LoginService) : LoginDataSource {

    override suspend fun login(username: CharSequence, password: CharSequence): LoginResponseDataModel =
        service.login(username, password)
}
