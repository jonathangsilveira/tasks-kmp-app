package edu.jgsilveira.tasks.kmp.data.repository

import edu.jgsilveira.tasks.kmp.data.datasource.NoteLocalDataSource
import edu.jgsilveira.tasks.kmp.domain.model.Note

internal class NoteRepositoryImpl(
    private val localDataSource: NoteLocalDataSource
) : NoteRepository {

    override suspend fun getNoteById(id: Long): Note? {
        return localDataSource.getNoteById(id)
    }

    override suspend fun getAllNotes(): List<Note> {
        return localDataSource.getAllNotes()
    }

    override suspend fun upsertNote(note: Note): Note {
        val generatedId = localDataSource.upsertNote(note)
        return note.copy(id = generatedId)
    }

    override suspend fun deleteNoteById(id: Long) {
        localDataSource.deleteNoteById(id)
    }

    override suspend fun markNoteAsDone(id: Long) {
        localDataSource.markNoteAsDone(id)
    }
}