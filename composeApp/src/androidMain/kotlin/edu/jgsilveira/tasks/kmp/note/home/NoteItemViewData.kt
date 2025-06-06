package edu.jgsilveira.tasks.kmp.note.home

import androidx.compose.runtime.Immutable

@Immutable
internal data class NoteItemViewData(
    val noteId: Long,
    val title: String,
    val description: String?,
    val isPinned: Boolean = false
)
