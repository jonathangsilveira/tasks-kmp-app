package edu.jgsilveira.tasks.kmp.features.notes.list

import androidx.compose.runtime.Immutable

internal sealed interface NoteListUIEffect {
    data object NavigateBack : NoteListUIEffect
    data object NewNote : NoteListUIEffect
    @Immutable
    data class OpenNote(
        val noteId: Long
    ) : NoteListUIEffect
}