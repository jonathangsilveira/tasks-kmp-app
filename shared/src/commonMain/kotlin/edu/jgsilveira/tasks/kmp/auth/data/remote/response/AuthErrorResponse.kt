package edu.jgsilveira.tasks.kmp.auth.data.remote.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AuthErrorResponse(
    @SerialName("detail") val detail: String
)
