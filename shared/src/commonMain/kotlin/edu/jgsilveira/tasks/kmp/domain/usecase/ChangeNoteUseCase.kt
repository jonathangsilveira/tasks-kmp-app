package edu.jgsilveira.tasks.kmp.domain.usecase

import edu.jgsilveira.tasks.kmp.data.repository.NoteRepository
import edu.jgsilveira.tasks.kmp.domain.model.NoteChanges
import edu.jgsilveira.tasks.kmp.domain.model.Note
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import kotlinx.datetime.Clock

class ChangeNoteUseCase(
    private val noteRepository: NoteRepository,
    private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    suspend operator fun invoke(noteId: Long, changes: NoteChanges): Note {
        return withContext(coroutineDispatcher) {
            val note = noteRepository.getNoteById(noteId)
                ?: error("Note not found")
            val nowMillis = Clock.System.now().toEpochMilliseconds()
            noteRepository.upsertNote(
                note.copy(
                    title = changes.title,
                    description = changes.description,
                    updatedAt = nowMillis
                )
            )
        }
    }
}