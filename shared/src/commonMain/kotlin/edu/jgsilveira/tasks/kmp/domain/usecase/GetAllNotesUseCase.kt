package edu.jgsilveira.tasks.kmp.domain.usecase

import edu.jgsilveira.tasks.kmp.data.repository.NoteRepository
import edu.jgsilveira.tasks.kmp.domain.model.Note
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext

class GetAllNotesUseCase(
    private val noteRepository: NoteRepository,
    private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    suspend operator fun invoke(): List<Note> {
        return withContext(coroutineDispatcher) {
            noteRepository.getAllNotes()
        }
    }
}