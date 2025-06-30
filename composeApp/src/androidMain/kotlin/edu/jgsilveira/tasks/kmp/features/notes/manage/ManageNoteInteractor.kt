package edu.jgsilveira.tasks.kmp.features.notes.manage

import edu.jgsilveira.tasks.kmp.domain.model.NoteChanges
import edu.jgsilveira.tasks.kmp.domain.usecase.ChangeNoteUseCase
import edu.jgsilveira.tasks.kmp.domain.usecase.GetNoteByIdUseCase
import edu.jgsilveira.tasks.kmp.domain.usecase.NewNoteUseCase

internal class ManageNoteInteractor(
    private val newNote: NewNoteUseCase,
    private val changeNote: ChangeNoteUseCase,
    private val getNoteById: GetNoteByIdUseCase
) {

    suspend fun saveNote(
        viewData: NoteChangesViewData
    ): NoteChangesViewData {
        val noteChanges = NoteChanges(
            title = viewData.title,
            description = viewData.description
        )
        val note = if (viewData.noteId == NEW_NOTE_ID) {
            newNote(noteChanges)
        } else {
            changeNote(viewData.noteId, noteChanges)
        }
        return viewData.copy(
            noteId = note.id
        )
    }

    suspend fun getNoteByIdOrNew(
        noteId: Long
    ): NoteChangesViewData {
        return getNoteById(noteId)?.let { note ->
            NoteChangesViewData(
                noteId = note.id,
                title = note.title,
                description = note.description
            )
        } ?: NoteChangesViewData()
    }
}