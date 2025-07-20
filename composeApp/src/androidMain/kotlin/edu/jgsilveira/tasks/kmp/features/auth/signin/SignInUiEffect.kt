package edu.jgsilveira.tasks.kmp.features.auth.signin

import edu.jgsilveira.tasks.kmp.core.arch.viewmodel.UIEffect

internal sealed interface SignInUiEffect : UIEffect {
    data object NavigateToSignUp : SignInUiEffect
    data object NavigateToHome : SignInUiEffect
}