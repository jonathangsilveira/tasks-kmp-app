package edu.jgsilveira.tasks.kmp.features.auth.signup

sealed interface SignUpUiAction {
    data class SignUpButtonClick(
        val fullname: String,
        val email: String,
        val password: String
    ) : SignUpUiAction

    data object CloseClick : SignUpUiAction
}