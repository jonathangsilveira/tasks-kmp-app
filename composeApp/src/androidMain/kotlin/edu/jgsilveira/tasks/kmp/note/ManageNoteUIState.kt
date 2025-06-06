package edu.jgsilveira.tasks.kmp.note

import androidx.compose.runtime.Immutable

internal sealed interface ManageNoteUIState {
    data object Initial : ManageNoteUIState
    @Immutable
    data class Content(
        val viewData: NoteChangesViewData
    ) : ManageNoteUIState
}