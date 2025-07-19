package edu.jgsilveira.tasks.kmp.auth.domain.repository

import edu.jgsilveira.tasks.kmp.auth.data.remote.request.SignInFormData
import edu.jgsilveira.tasks.kmp.auth.data.remote.request.SignUpFormData
import edu.jgsilveira.tasks.kmp.auth.data.remote.service.AuthApiService
import edu.jgsilveira.tasks.kmp.auth.domain.model.AuthToken
import edu.jgsilveira.tasks.kmp.auth.domain.model.SignInForm
import edu.jgsilveira.tasks.kmp.auth.domain.model.SignUpForm

internal class AuthRepositoryImpl(
    private val apiService: AuthApiService
) : AuthRepository {

    override suspend fun signUp(form: SignUpForm) {
        val formData = SignUpFormData(
            fullName = form.fullName,
            email = form.email,
            password = form.password
        )
        apiService.signUp(formData)
    }

    override suspend fun signIn(form: SignInForm): AuthToken {
        val formData = with(form) {
            SignInFormData(
                email = email,
                password = password
            )
        }
        val authToken = apiService.signIn(formData)
        return with(authToken) {
            AuthToken(
                accessToken = accessToken.orEmpty(),
                tokenType = tokenType.orEmpty()
            )
        }
    }
}