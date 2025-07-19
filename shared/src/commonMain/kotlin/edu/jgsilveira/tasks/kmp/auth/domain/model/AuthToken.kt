package edu.jgsilveira.tasks.kmp.auth.domain.model

data class AuthToken(
    val accessToken: String,
    val tokenType: String
)