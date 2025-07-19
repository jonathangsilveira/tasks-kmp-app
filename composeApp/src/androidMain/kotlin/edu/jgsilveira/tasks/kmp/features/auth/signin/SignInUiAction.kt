package edu.jgsilveira.tasks.kmp.features.auth.signin

internal sealed interface SignInUiAction {
    data class SignInClick(
        val email: String,
        val password: String
    ) : SignInUiAction

    data object SignUpClick : SignInUiAction

    data object RetrySignIn : SignInUiAction
}