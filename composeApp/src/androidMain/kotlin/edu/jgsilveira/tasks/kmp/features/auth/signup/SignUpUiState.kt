package edu.jgsilveira.tasks.kmp.features.auth.signup

import edu.jgsilveira.tasks.kmp.core.arch.viewmodel.UIState
import org.jetbrains.compose.resources.StringResource

internal sealed interface SignUpUiState : UIState {
    data object Initial : SignUpUiState
    data object Processing : SignUpUiState
    data object Success : SignUpUiState
    data class Error(
        val messageRes: StringResource
    ) : SignUpUiState
}