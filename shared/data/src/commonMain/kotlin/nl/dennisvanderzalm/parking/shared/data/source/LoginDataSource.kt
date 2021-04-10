package nl.dennisvanderzalm.parking.shared.data.source

import nl.dennisvanderzalm.parking.shared.data.model.login.LoginResponseDataModel

interface LoginDataSource {

    suspend fun login(username: CharSequence, password: CharSequence): LoginResponseDataModel
}
