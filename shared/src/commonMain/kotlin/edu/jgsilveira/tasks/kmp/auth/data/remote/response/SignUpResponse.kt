package edu.jgsilveira.tasks.kmp.auth.data.remote.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SignUpResponse(
    @SerialName("message") val message: String? = null
)
