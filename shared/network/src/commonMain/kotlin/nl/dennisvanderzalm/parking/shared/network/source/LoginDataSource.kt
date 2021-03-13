package nl.dennisvanderzalm.parking.shared.network.source

interface LoginDataSource {

    suspend fun login(username: CharSequence, password: CharSequence)
}