package edu.jgsilveira.tasks.kmp.auth.domain.usecase

import edu.jgsilveira.tasks.kmp.auth.domain.model.SignUpForm
import edu.jgsilveira.tasks.kmp.auth.domain.repository.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class SignUpUseCase(
    private val authRepository: AuthRepository,
    private val coroutineContext: CoroutineContext = Dispatchers.IO
) {

    suspend operator fun invoke(form: SignUpForm): Result<Unit> {
        return runCatching {
            withContext(coroutineContext) {
                authRepository.signUp(form)
            }
        }.onFailure { error ->
            println("[ERROR] Fail on signing up: ${error.message}")
        }
    }
}