package edu.jgsilveira.tasks.kmp.features.notes.list

import androidx.compose.runtime.Immutable

@Immutable
internal data class NoteItemViewData(
    val noteId: Long,
    val title: String,
    val description: String?,
    val isPinned: Boolean = false
)
