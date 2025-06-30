package edu.jgsilveira.tasks.kmp.features.notes.list

import edu.jgsilveira.tasks.kmp.domain.usecase.GetAllNotesUseCase

internal class NoteListInteractor(
    private val getAllNotesUseCase: GetAllNotesUseCase
) {

    suspend fun getAllNotes(): Result<List<NoteItemViewData>> {
        return runCatching {
            getAllNotesUseCase()
        }.map { notes ->
            notes.map { note ->
                NoteItemViewData(
                    noteId = note.id,
                    title = note.title,
                    description = note.description,
                    isPinned = false
                )
            }
        }
    }
}