package edu.jgsilveira.tasks.kmp.features.auth.signin

import androidx.compose.runtime.Immutable
import edu.jgsilveira.tasks.kmp.core.arch.viewmodel.UIState
import org.jetbrains.compose.resources.StringResource

internal sealed interface SignInUiState : UIState {
    data object Initial : SignInUiState
    data object Loading : SignInUiState
    @Immutable
    data class Error(
        val messageRes: StringResource
    ) : SignInUiState
}