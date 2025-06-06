package edu.jgsilveira.tasks.kmp.note

import androidx.compose.runtime.Immutable

internal const val NEW_NOTE_ID = 0L

@Immutable
internal data class NoteChangesViewData(
    val noteId: Long = NEW_NOTE_ID,
    val title: String = "",
    val description: String? = null
)
