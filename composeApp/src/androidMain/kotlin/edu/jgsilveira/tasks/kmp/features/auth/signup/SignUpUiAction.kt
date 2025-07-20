package edu.jgsilveira.tasks.kmp.features.auth.signup

import edu.jgsilveira.tasks.kmp.core.arch.viewmodel.UIAction

internal sealed interface SignUpUiAction : UIAction {
    data class SignUpButtonClick(
        val fullName: String,
        val email: String,
        val password: String
    ) : SignUpUiAction

    data object CloseClick : SignUpUiAction
    data object RetrySignUp : SignUpUiAction
}