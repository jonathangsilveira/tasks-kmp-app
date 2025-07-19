package edu.jgsilveira.tasks.kmp.features.auth.signup

sealed interface SignUpUiEffect {
    data object NavigateUp : SignUpUiEffect
}