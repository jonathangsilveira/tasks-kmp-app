package edu.jgsilveira.tasks.kmp.features.auth.signup

import org.jetbrains.compose.resources.StringResource

sealed interface SignUpUiState {
    data object Initial : SignUpUiState
    data object Processing : SignUpUiState
    data object Success : SignUpUiState
    data class Error(
        val messageRes: StringResource
    ) : SignUpUiState
}