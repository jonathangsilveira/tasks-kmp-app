package edu.jgsilveira.tasks.kmp.data

import edu.jgsilveira.tasks.kmp.domain.model.NEW_NOTE_ID
import edu.jgsilveira.tasks.kmp.domain.model.Note
import kotlinx.datetime.Clock
import kotlin.concurrent.atomics.AtomicLong
import kotlin.concurrent.atomics.ExperimentalAtomicApi
import kotlin.concurrent.atomics.incrementAndFetch

@OptIn(ExperimentalAtomicApi::class)
internal class InMemoryNoteRepository : NoteRepository {
    private val sequence = AtomicLong(0)
    private val notes: MutableMap<Long, Note> = mutableMapOf()

    override suspend fun getNoteById(id: Long): Note? {
        return notes[id]
    }

    override suspend fun getAllNotes(): List<Note> {
        return notes.values.toList()
    }

    override suspend fun upsertNote(note: Note): Note {
        if (note.id == NEW_NOTE_ID) {
            val new = note.copy(
                id = sequence.incrementAndFetch()
            )
            notes[new.id] = new
            return new
        }
        val update = note.copy(
            updatedAt = Clock.System.now().toEpochMilliseconds()
        )
        notes[note.id] = update
        return update
    }

    override suspend fun deleteNoteById(id: Long) {
        notes.remove(id)
    }
}