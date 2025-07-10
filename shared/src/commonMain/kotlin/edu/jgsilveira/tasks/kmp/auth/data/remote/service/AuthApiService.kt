package edu.jgsilveira.tasks.kmp.auth.data.remote.service

import edu.jgsilveira.tasks.kmp.auth.data.remote.request.SignInFormData
import edu.jgsilveira.tasks.kmp.auth.data.remote.request.SignUpFormData
import edu.jgsilveira.tasks.kmp.auth.data.remote.response.AuthTokenResponse
import edu.jgsilveira.tasks.kmp.auth.data.remote.response.SignUpResponse

internal interface AuthApiService {
    suspend fun signUp(formData: SignUpFormData): SignUpResponse
    suspend fun signIn(formData: SignInFormData): AuthTokenResponse
}