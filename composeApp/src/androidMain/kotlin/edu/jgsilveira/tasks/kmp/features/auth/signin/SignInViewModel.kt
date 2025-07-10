package edu.jgsilveira.tasks.kmp.features.auth.signin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.jgsilveira.tasks.kmp.auth.domain.model.SignInForm
import edu.jgsilveira.tasks.kmp.auth.domain.usecase.SignInUseCase
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import taskskmpapp.composeapp.generated.resources.Res
import taskskmpapp.composeapp.generated.resources.generic_error_message

internal class SignInViewModel(
    private val signInUseCase: SignInUseCase
) : ViewModel() {

    private val mutableState = MutableStateFlow<SignInUiState>(
        SignInUiState.Initial
    )
    val uiState: StateFlow<SignInUiState>
        get() = mutableState

    private val uiEffectChannel = Channel<SignInUiEffect>(
        capacity = Channel.BUFFERED,
        onBufferOverflow = BufferOverflow.SUSPEND
    )
    val uiEffects: Flow<SignInUiEffect>
        get() = uiEffectChannel.receiveAsFlow()

    fun dispatchAction(uiAction: SignInUiAction) {
        when (uiAction) {
            is SignInUiAction.SignUpClick -> {
                uiEffectChannel.trySend(SignInUiEffect.NavigateToSignUp)
            }
            is SignInUiAction.SignInClick -> onSignInClicked(uiAction)
            SignInUiAction.RetrySignIn -> retry()
        }
    }

    private fun onSignInClicked(uiAction: SignInUiAction.SignInClick) {
        viewModelScope.launch {
            mutableState.emit(SignInUiState.Loading)
            val form = with(uiAction) {
                SignInForm(email, password)
            }
            signInUseCase(form)
                .onSuccess {
                    uiEffectChannel.trySend(SignInUiEffect.NavigateToHome)
                }
                .onFailure {
                    mutableState.emit(
                        SignInUiState.Error(Res.string.generic_error_message)
                    )
                }
        }
    }

    private fun retry() {
        viewModelScope.launch {
            mutableState.emit(SignInUiState.Initial)
        }
    }
}