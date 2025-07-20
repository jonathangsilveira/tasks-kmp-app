package edu.jgsilveira.tasks.kmp.features.auth.signin

import edu.jgsilveira.tasks.kmp.core.arch.viewmodel.UIAction

internal sealed interface SignInUiAction : UIAction {
    data class SignInClick(
        val email: String,
        val password: String
    ) : SignInUiAction

    data object SignUpClick : SignInUiAction

    data object RetrySignIn : SignInUiAction
}