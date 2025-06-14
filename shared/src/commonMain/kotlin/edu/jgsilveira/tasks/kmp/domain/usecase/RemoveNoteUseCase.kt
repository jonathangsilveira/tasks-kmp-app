package edu.jgsilveira.tasks.kmp.domain.usecase

import edu.jgsilveira.tasks.kmp.data.repository.NoteRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext

class RemoveNoteUseCase(
    private val noteRepository: NoteRepository,
    private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    suspend fun invoke(id: Long) {
        return withContext(coroutineDispatcher) {
            noteRepository.deleteNoteById(id)
        }
    }
}