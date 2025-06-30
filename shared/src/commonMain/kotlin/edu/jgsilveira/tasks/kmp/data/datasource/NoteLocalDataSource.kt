package edu.jgsilveira.tasks.kmp.data.datasource

import edu.jgsilveira.tasks.kmp.domain.model.Note

internal interface NoteLocalDataSource {
    suspend fun getNoteById(id: Long): Note?
    suspend fun getAllNotes(): List<Note>
    suspend fun upsertNote(note: Note): Long
    suspend fun deleteNoteById(id: Long)
    suspend fun markNoteAsDone(id: Long)
}