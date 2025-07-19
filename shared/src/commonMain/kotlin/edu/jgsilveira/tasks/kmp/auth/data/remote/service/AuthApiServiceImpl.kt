package edu.jgsilveira.tasks.kmp.auth.data.remote.service

import edu.jgsilveira.tasks.kmp.auth.data.remote.request.SignInFormData
import edu.jgsilveira.tasks.kmp.auth.data.remote.request.SignUpFormData
import edu.jgsilveira.tasks.kmp.auth.data.remote.response.AuthErrorResponse
import edu.jgsilveira.tasks.kmp.auth.data.remote.response.AuthTokenResponse
import edu.jgsilveira.tasks.kmp.auth.data.remote.response.SignUpResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.forms.submitForm
import io.ktor.http.HttpStatusCode
import io.ktor.http.parameters

internal class AuthApiServiceImpl(
    private val client: HttpClient
) : AuthApiService {

    override suspend fun signUp(formData: SignUpFormData): SignUpResponse {
        val response = client.submitForm(
            url = "/api/v1/signup",
            formParameters = parameters {
                append("full_name", formData.fullName)
                append("email", formData.email)
                append("password", formData.password)
            }
        )
        if (response.status > HttpStatusCode.MultiStatus) {
            val errorBody = response.body<AuthErrorResponse>()
            error(message = errorBody.detail)
        }
        return response.body<SignUpResponse>()
    }

    override suspend fun signIn(formData: SignInFormData): AuthTokenResponse {
        val response = client.submitForm(
            url = "/api/v1/signin",
            formParameters = parameters {
                append("username", formData.email)
                append("password", formData.password)
            }
        )
        if (response.status > HttpStatusCode.MultiStatus) {
            val errorResponse = response.body<AuthErrorResponse>()
            error(message = errorResponse.detail)
        }
        return response.body<AuthTokenResponse>()
    }
}