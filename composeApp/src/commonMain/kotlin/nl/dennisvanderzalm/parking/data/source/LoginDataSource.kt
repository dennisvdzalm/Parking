package nl.dennisvanderzalm.parking.data.source

import nl.dennisvanderzalm.parking.data.model.login.LoginResponseDataModel

interface LoginDataSource {

    suspend fun login(username: CharSequence, password: CharSequence): LoginResponseDataModel
}
