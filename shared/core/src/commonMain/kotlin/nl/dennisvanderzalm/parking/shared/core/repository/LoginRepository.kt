package nl.dennisvanderzalm.parking.shared.core.repository

interface LoginRepository {

    suspend fun login(username: CharSequence, password: CharSequence)
}
