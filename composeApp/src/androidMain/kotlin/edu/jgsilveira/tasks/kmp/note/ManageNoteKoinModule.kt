package edu.jgsilveira.tasks.kmp.note

import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

internal val manageNoteKoinModule = module {
    factoryOf(::ManageNoteInteractor)

    viewModel { (noteId: Long) ->
        ManageNoteViewModel(
            noteId = noteId,
            interactor = get()
        )
    }
}