package edu.jgsilveira.tasks.kmp.note.home

import androidx.compose.runtime.Immutable

internal sealed interface NoteListUIEffect {
    data object NavigateBack : NoteListUIEffect
    data object NewNote : NoteListUIEffect
    @Immutable
    data class OpenNote(
        val noteId: Long
    ) : NoteListUIEffect
}