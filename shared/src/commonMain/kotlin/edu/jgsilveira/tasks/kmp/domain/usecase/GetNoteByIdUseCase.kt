package edu.jgsilveira.tasks.kmp.domain.usecase

import edu.jgsilveira.tasks.kmp.data.NoteRepository
import edu.jgsilveira.tasks.kmp.domain.model.Note
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext

class GetNoteByIdUseCase(
    private val noteRepository: NoteRepository,
    private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    suspend operator fun invoke(id: Long): Note? {
        return withContext(coroutineDispatcher) {
            noteRepository.getNoteById(id)
        }
    }
}