package nl.dennisvanderzalm.parking.shared.data.source

interface LoginDataSource {

    suspend fun login(username: CharSequence, password: CharSequence)
}