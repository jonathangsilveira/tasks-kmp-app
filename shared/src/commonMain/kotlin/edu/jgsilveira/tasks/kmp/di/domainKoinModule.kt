package edu.jgsilveira.tasks.kmp.di

import edu.jgsilveira.tasks.kmp.data.InMemoryNoteRepository
import edu.jgsilveira.tasks.kmp.data.NoteRepository
import edu.jgsilveira.tasks.kmp.domain.usecase.ChangeNoteUseCase
import edu.jgsilveira.tasks.kmp.domain.usecase.GetAllNotesUseCase
import edu.jgsilveira.tasks.kmp.domain.usecase.GetNoteByIdUseCase
import edu.jgsilveira.tasks.kmp.domain.usecase.NewNoteUseCase
import edu.jgsilveira.tasks.kmp.domain.usecase.RemoveNoteUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val domainKoinModule = module {
    singleOf(::InMemoryNoteRepository) bind NoteRepository::class

    factory {
        GetNoteByIdUseCase(
            noteRepository = get(),
            coroutineDispatcher = Dispatchers.IO
        )
    }

    factory {
        GetAllNotesUseCase(
            noteRepository = get(),
            coroutineDispatcher = Dispatchers.IO
        )
    }

    factory {
        NewNoteUseCase(
            noteRepository = get(),
            coroutineDispatcher = Dispatchers.IO
        )
    }

    factory {
        ChangeNoteUseCase(
            noteRepository = get(),
            coroutineDispatcher = Dispatchers.IO
        )
    }

    factory {
        RemoveNoteUseCase(
            noteRepository = get(),
            coroutineDispatcher = Dispatchers.IO
        )
    }
}