package edu.jgsilveira.tasks.kmp.auth.domain.usecase

import edu.jgsilveira.tasks.kmp.auth.domain.model.SignInForm
import edu.jgsilveira.tasks.kmp.auth.domain.repository.AuthRepository
import edu.jgsilveira.tasks.kmp.auth.domain.repository.InMemoryTokenRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class SignInUseCase(
    private val authRepository: AuthRepository,
    private val coroutineContext: CoroutineContext = Dispatchers.IO
) {

    suspend operator fun invoke(form: SignInForm): Result<Unit> {
        return runCatching {
            val authToken = withContext(coroutineContext) {
                authRepository.signIn(form)
            }
            InMemoryTokenRepository.updateToken(authToken)
        }.onSuccess {
            InMemoryTokenRepository.setLogged(true)
        }.onFailure {
            InMemoryTokenRepository.setLogged(false)
        }
    }
}