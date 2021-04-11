package nl.dennisvanderzalm.parking.shared.core.repositories

interface SessionRepository {

    fun isSessionActive(): Boolean
}
