package edu.jgsilveira.tasks.kmp.features.auth.signin

import androidx.lifecycle.viewModelScope
import edu.jgsilveira.tasks.kmp.auth.domain.model.SignInForm
import edu.jgsilveira.tasks.kmp.auth.domain.usecase.SignInUseCase
import edu.jgsilveira.tasks.kmp.core.arch.viewmodel.UIViewModel
import kotlinx.coroutines.launch
import taskskmpapp.composeapp.generated.resources.Res
import taskskmpapp.composeapp.generated.resources.generic_error_message

internal class SignInViewModel(
    private val signInUseCase: SignInUseCase
) : UIViewModel<SignInUiState, SignInUiEffect, SignInUiAction>(SignInUiState.Initial) {

    override fun dispatchAction(uiAction: SignInUiAction) {
        when (uiAction) {
            is SignInUiAction.SignUpClick -> {
                sendEffect(SignInUiEffect.NavigateToSignUp)
            }
            is SignInUiAction.SignInClick -> onSignInClicked(uiAction)
            SignInUiAction.RetrySignIn -> retry()
        }
    }

    private fun onSignInClicked(uiAction: SignInUiAction.SignInClick) {
        viewModelScope.launch {
            setState { SignInUiState.Loading }
            val form = with(uiAction) {
                SignInForm(email, password)
            }
            signInUseCase(form)
                .onSuccess {
                    setState { SignInUiState.Initial }
                    sendEffect(SignInUiEffect.NavigateToHome)
                }
                .onFailure {
                    setState {
                        SignInUiState.Error(Res.string.generic_error_message)
                    }
                }
        }
    }

    private fun retry() {
        setState { SignInUiState.Initial }
    }
}