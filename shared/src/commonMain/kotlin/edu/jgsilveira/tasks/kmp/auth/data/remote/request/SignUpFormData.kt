package edu.jgsilveira.tasks.kmp.auth.data.remote.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SignUpFormData(
    @SerialName("full_name") val fullName: String,
    @SerialName("email") val email: String,
    @SerialName("password") val password: String
)
