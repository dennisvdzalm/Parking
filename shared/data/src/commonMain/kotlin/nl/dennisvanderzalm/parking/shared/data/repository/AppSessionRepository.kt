package nl.dennisvanderzalm.parking.shared.data.repository

import nl.dennisvanderzalm.parking.shared.core.repository.SessionRepository
import nl.dennisvanderzalm.parking.shared.data.session.SessionManager

class AppSessionRepository(private val sessionManager: SessionManager) : SessionRepository {
    override fun isSessionActive(): Boolean = sessionManager.isSessionActive
}
