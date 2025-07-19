package edu.jgsilveira.tasks.kmp.auth.domain.repository

import edu.jgsilveira.tasks.kmp.auth.domain.model.AuthToken

internal object InMemoryTokenRepository {
    private var accessToken: String? = null
    private var tokenType: String? = null
    var isLogged: Boolean = false
        private set

    fun updateToken(authToken: AuthToken) {
        accessToken = authToken.accessToken
        tokenType = authToken.tokenType
    }

    fun getAuthorization(): String {
        return "${tokenType.orEmpty()} ${accessToken.orEmpty()}"
    }

    fun setLogged(isLogged: Boolean) {
        InMemoryTokenRepository.isLogged = isLogged
    }
}