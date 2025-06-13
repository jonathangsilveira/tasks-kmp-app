package edu.jgsilveira.tasks.kmp.note.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

internal class NoteListViewModel(
    private val interactor: NoteListInteractor
) : ViewModel() {
    private val mutableState = MutableStateFlow<NoteListUIState>(
        NoteListUIState.Initial
    )
    val uiState: StateFlow<NoteListUIState>
        get() = mutableState

    private val uiEffectChannel = Channel<NoteListUIEffect>(
        capacity = Channel.BUFFERED,
        onBufferOverflow = BufferOverflow.SUSPEND
    )
    val uiEffects: Flow<NoteListUIEffect>
        get() = uiEffectChannel.receiveAsFlow()

    fun fetchAllNotes() {
        viewModelScope.launch {
            mutableState.value = NoteListUIState.Loading
            interactor.getAllNotes()
                .onSuccess { items ->
                    val newState = if (items.isEmpty()) {
                        NoteListUIState.Empty
                    } else {
                        NoteListUIState.Content(items)
                    }
                    mutableState.value = newState
                }
                .onFailure {
                    val newState = NoteListUIState.Error
                    mutableState.value = newState
                }
        }
    }

    fun openNote(note: NoteItemViewData) {
        viewModelScope.launch {
            uiEffectChannel.trySend(
                NoteListUIEffect.OpenNote(note.noteId)
            )
        }
    }

    fun newNote() {
        viewModelScope.launch {
            uiEffectChannel.trySend(
                NoteListUIEffect.NewNote
            )
        }
    }

    fun navigateToPrevious() {
        viewModelScope.launch {
            uiEffectChannel.trySend(
                NoteListUIEffect.NavigateBack
            )
        }
    }
}