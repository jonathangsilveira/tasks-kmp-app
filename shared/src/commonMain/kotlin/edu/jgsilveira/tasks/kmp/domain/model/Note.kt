package edu.jgsilveira.tasks.kmp.domain.model

const val NEW_NOTE_ID = 0L

data class Note(
    val id: Long,
    val title: String,
    val description: String?,
    val createdAt: Long,
    val updatedAt: Long,
    val isDone: Boolean
)
