package edu.jgsilveira.tasks.kmp.domain.usecase

import edu.jgsilveira.tasks.kmp.data.repository.NoteRepository
import edu.jgsilveira.tasks.kmp.domain.model.NoteChanges
import edu.jgsilveira.tasks.kmp.domain.model.NEW_NOTE_ID
import edu.jgsilveira.tasks.kmp.domain.model.Note
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import kotlinx.datetime.Clock
import kotlin.coroutines.CoroutineContext

class NewNoteUseCase(
    private val noteRepository: NoteRepository,
    private val coroutineDispatcher: CoroutineContext = Dispatchers.IO
) {

    suspend operator fun invoke(changes: NoteChanges): Note {
        return withContext(coroutineDispatcher) {
            val nowMillis = Clock.System.now().toEpochMilliseconds()
            val note = Note(
                id = NEW_NOTE_ID,
                title = changes.title,
                description = changes.description,
                createdAt = nowMillis,
                updatedAt = nowMillis,
                isDone = false
            )
            noteRepository.upsertNote(note)
        }
    }
}