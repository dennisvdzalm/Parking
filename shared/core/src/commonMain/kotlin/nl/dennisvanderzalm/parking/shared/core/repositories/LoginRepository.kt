package nl.dennisvanderzalm.parking.shared.core.repositories

interface LoginRepository {

    suspend fun login(username: CharSequence, password: CharSequence)
}