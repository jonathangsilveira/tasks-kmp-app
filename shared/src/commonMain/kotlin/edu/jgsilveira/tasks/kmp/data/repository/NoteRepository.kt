package edu.jgsilveira.tasks.kmp.data.repository

import edu.jgsilveira.tasks.kmp.domain.model.Note

interface NoteRepository {
    suspend fun getNoteById(id: Long): Note?
    suspend fun getAllNotes(): List<Note>
    suspend fun upsertNote(note: Note): Note
    suspend fun deleteNoteById(id: Long)
    suspend fun markNoteAsDone(id: Long)
}