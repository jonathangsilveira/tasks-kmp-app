package edu.jgsilveira.tasks.kmp.features.auth.signin

internal sealed interface SignInUiEffect {
    data object NavigateToSignUp : SignInUiEffect
    data object NavigateToHome : SignInUiEffect
}