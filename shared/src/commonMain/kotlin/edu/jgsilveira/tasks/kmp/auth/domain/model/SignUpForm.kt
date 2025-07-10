package edu.jgsilveira.tasks.kmp.auth.domain.model

data class SignUpForm(
    val fullName: String,
    val email: String,
    val password: String
)
