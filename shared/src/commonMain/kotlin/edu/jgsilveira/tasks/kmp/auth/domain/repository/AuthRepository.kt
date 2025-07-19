package edu.jgsilveira.tasks.kmp.auth.domain.repository

import edu.jgsilveira.tasks.kmp.auth.domain.model.AuthToken
import edu.jgsilveira.tasks.kmp.auth.domain.model.SignInForm
import edu.jgsilveira.tasks.kmp.auth.domain.model.SignUpForm

interface AuthRepository {
    suspend fun signUp(form: SignUpForm)
    suspend fun signIn(form: SignInForm): AuthToken
}