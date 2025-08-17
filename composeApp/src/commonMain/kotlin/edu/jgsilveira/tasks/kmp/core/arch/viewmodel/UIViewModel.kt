package edu.jgsilveira.tasks.kmp.core.arch.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal abstract class UIViewModel<STATE: UIState, EFFECT: UIEffect, ACTION: UIAction>(
    initialState: STATE
) : ViewModel() {

    private val mutableState = MutableStateFlow(initialState)
    val uiState: StateFlow<STATE>
        get() = mutableState

    private val uiEffectChannel = Channel<EFFECT>(
        capacity = Channel.BUFFERED,
        onBufferOverflow = BufferOverflow.SUSPEND
    )
    val uiEffects: Flow<EFFECT>
        get() = uiEffectChannel.receiveAsFlow()

    abstract fun dispatchAction(uiAction: ACTION)

    protected fun setState(reducer: (currentState: STATE) -> STATE) {
        mutableState.update(reducer)
    }

    protected fun sendEffect(uiEffect: EFFECT) {
        uiEffectChannel.trySend(uiEffect)
    }
}