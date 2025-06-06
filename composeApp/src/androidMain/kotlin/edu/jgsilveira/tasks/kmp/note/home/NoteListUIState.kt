package edu.jgsilveira.tasks.kmp.note.home

import androidx.compose.runtime.Immutable

internal sealed interface NoteListUIState {
    data object Initial : NoteListUIState
    data object Loading : NoteListUIState
    @Immutable
    data class Content(
        val notes: List<NoteItemViewData>
    ) : NoteListUIState
    data object Empty : NoteListUIState
    data object Error: NoteListUIState
}