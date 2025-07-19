package edu.jgsilveira.tasks.kmp.features.auth.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.jgsilveira.tasks.kmp.auth.domain.model.SignUpForm
import edu.jgsilveira.tasks.kmp.auth.domain.usecase.SignUpUseCase
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import taskskmpapp.composeapp.generated.resources.Res
import taskskmpapp.composeapp.generated.resources.generic_error_message

internal class SignUpViewModel(
    private val signUpUseCase: SignUpUseCase
): ViewModel() {

    private val mutableState = MutableStateFlow<SignUpUiState>(
        SignUpUiState.Initial
    )
    val uiState: StateFlow<SignUpUiState>
        get() = mutableState

    private val uiEffectChannel = Channel<SignUpUiEffect>(
        capacity = Channel.BUFFERED,
        onBufferOverflow = BufferOverflow.SUSPEND
    )
    val uiEffects: Flow<SignUpUiEffect>
        get() = uiEffectChannel.receiveAsFlow()

    fun dispatchAction(uiAction: SignUpUiAction) {
        when (uiAction) {
            is SignUpUiAction.SignUpButtonClick -> signUp(uiAction)
            SignUpUiAction.CloseClick -> {
                uiEffectChannel.trySend(SignUpUiEffect.NavigateUp)
            }
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
            mutableState.emit(SignUpUiState.Processing)
            signUpUseCase(form)
                .onSuccess {
                    mutableState.emit(SignUpUiState.Success)
                    delay(300)
                    uiEffectChannel.trySend(SignUpUiEffect.NavigateUp)
                }
                .onFailure {
                    mutableState.emit(
                        SignUpUiState.Error(
                            Res.string.generic_error_message
                        )
                    )
                }
        }
    }

    private fun retry() {
        viewModelScope.launch {
            mutableState.emit(SignUpUiState.Initial)
        }
    }
}