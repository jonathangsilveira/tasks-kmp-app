package edu.jgsilveira.tasks.kmp.note

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
import taskskmpapp.composeapp.generated.resources.Res
import taskskmpapp.composeapp.generated.resources.note_saved

internal class ManageNoteViewModel(
    private val noteId: Long,
    private val interactor: ManageNoteInteractor
) : ViewModel() {

    private val mutableState = MutableStateFlow<ManageNoteUIState>(
        ManageNoteUIState.Initial
    )
    val uiState: StateFlow<ManageNoteUIState>
        get() = mutableState

    private val uiEffectChannel = Channel<ManageNoteUIEffect>(
        capacity = Channel.BUFFERED,
        onBufferOverflow = BufferOverflow.SUSPEND
    )
    val uiEffects: Flow<ManageNoteUIEffect>
        get() = uiEffectChannel.receiveAsFlow()

    fun getNote() {
        val currentState = mutableState.value as? ManageNoteUIState.Content
        val viewDataNoteId = currentState?.viewData?.noteId
        val id = viewDataNoteId ?: noteId
        viewModelScope.launch {
            val viewData = interactor.getNoteByIdOrNew(id)
            mutableState.value = ManageNoteUIState.Content(
                viewData
            )
        }
    }

    fun updateTitle(title: String?) {
        mutableState.update { currentState ->
            val uiState = currentState as? ManageNoteUIState.Content
                ?: return@update currentState
            uiState.copy(
                viewData = uiState.viewData.copy(
                    title = title.orEmpty()
                )
            )
        }
    }

    fun updateDescription(description: String?) {
        mutableState.update { currentState ->
            val uiState = currentState as? ManageNoteUIState.Content
                ?: return@update currentState
            uiState.copy(
                viewData = uiState.viewData.copy(
                    description = description
                )
            )
        }
    }

    fun saveNote() {
        val content = mutableState.value as? ManageNoteUIState.Content ?: return
        viewModelScope.launch {
            val newViewData = interactor.saveNote(
                content.viewData
            )
            mutableState.value = ManageNoteUIState.Content(
                newViewData
            )
            uiEffectChannel.trySend(
                ManageNoteUIEffect.ShowSnackBar(
                    messageRes = Res.string.note_saved
                )
            )
        }
    }

    fun navigateBack() {
        viewModelScope.launch {
            uiEffectChannel.trySend(
                ManageNoteUIEffect.NavigateBack
            )
        }
    }
}