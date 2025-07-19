package edu.jgsilveira.tasks.kmp.features.auth.signup

sealed interface SignUpUiAction {
    data class SignUpButtonClick(
        val fullName: String,
        val email: String,
        val password: String
    ) : SignUpUiAction

    data object CloseClick : SignUpUiAction
    data object RetrySignUp : SignUpUiAction
}