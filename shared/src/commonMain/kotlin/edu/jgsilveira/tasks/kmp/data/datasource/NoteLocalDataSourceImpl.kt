package edu.jgsilveira.tasks.kmp.data.datasource

import edu.jgsilveira.tasks.kmp.domain.factory.NoteFactory
import edu.jgsilveira.tasks.kmp.domain.model.Note
import edu.jgsilveira.tasks.kmp.roomdb.dao.NoteDao

internal class NoteLocalDataSourceImpl(
    private val dao: NoteDao
) : NoteLocalDataSource {

    override suspend fun getNoteById(id: Long): Note? {
        val entity = dao.getNoteById(id) ?: return null
        return NoteFactory.fromEntity(entity)
    }

    override suspend fun getAllNotes(): List<Note> {
        val notes = dao.getAllNotes()
        return notes.map { NoteFactory.fromEntity(it) }
    }

    override suspend fun upsertNote(note: Note): Long {
        val entity = NoteFactory.toEntity(note)
        val id = dao.upsertNote(entity)
        return id
    }

    override suspend fun deleteNoteById(id: Long) {
        dao.deleteNoteById(id)
    }

    override suspend fun markNoteAsDone(id: Long) {
        val entity = dao.getNoteById(id) ?: return
        val changedEntity = entity.copy(isDone = true)
        dao.upsertNote(changedEntity)
    }
}