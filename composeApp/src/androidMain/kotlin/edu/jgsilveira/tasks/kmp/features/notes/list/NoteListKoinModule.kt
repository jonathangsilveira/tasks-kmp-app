package edu.jgsilveira.tasks.kmp.features.notes.list

import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

internal val noteListKoinModule = module {
    factoryOf(::NoteListInteractor)

    viewModel {
        NoteListViewModel(
            interactor = get()
        )
    }
}