package edu.jgsilveira.tasks.kmp.features.auth.signup

import androidx.lifecycle.viewModelScope
import edu.jgsilveira.tasks.kmp.auth.domain.model.SignUpForm
import edu.jgsilveira.tasks.kmp.auth.domain.usecase.SignUpUseCase
import edu.jgsilveira.tasks.kmp.core.arch.viewmodel.UIViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import taskskmpapp.composeapp.generated.resources.Res
import taskskmpapp.composeapp.generated.resources.generic_error_message

internal class SignUpViewModel(
    private val signUpUseCase: SignUpUseCase
): UIViewModel<SignUpUiState, SignUpUiEffect, SignUpUiAction>(SignUpUiState.Initial) {

    override fun dispatchAction(uiAction: SignUpUiAction) {
        when (uiAction) {
            is SignUpUiAction.SignUpButtonClick -> signUp(uiAction)
            SignUpUiAction.CloseClick -> sendEffect(SignUpUiEffect.NavigateUp)
            SignUpUiAction.RetrySignUp -> retry()
        }
    }

    private fun signUp(uiAction: SignUpUiAction.SignUpButtonClick) {
        val form = with(uiAction) {
            SignUpForm(
                fullName = fullName,
                email = email,
                password = password
            )
        }
        viewModelScope.launch {
            setState { SignUpUiState.Processing }
            signUpUseCase(form)
                .onSuccess {
                    setState { SignUpUiState.Success }
                    delay(300)
                    sendEffect(SignUpUiEffect.NavigateUp)
                }
                .onFailure {
                    setState {
                        SignUpUiState.Error(
                            Res.string.generic_error_message
                        )
                    }
                }
        }
    }

    private fun retry() {
        viewModelScope.launch {
            setState { SignUpUiState.Initial }
        }
    }
}