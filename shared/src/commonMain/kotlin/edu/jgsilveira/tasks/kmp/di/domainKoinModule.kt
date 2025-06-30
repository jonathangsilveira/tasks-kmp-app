package edu.jgsilveira.tasks.kmp.di

import edu.jgsilveira.tasks.kmp.data.datasource.NoteLocalDataSource
import edu.jgsilveira.tasks.kmp.data.datasource.NoteLocalDataSourceImpl
import edu.jgsilveira.tasks.kmp.data.repository.NoteRepository
import edu.jgsilveira.tasks.kmp.data.repository.NoteRepositoryImpl
import edu.jgsilveira.tasks.kmp.domain.usecase.ChangeNoteUseCase
import edu.jgsilveira.tasks.kmp.domain.usecase.GetAllNotesUseCase
import edu.jgsilveira.tasks.kmp.domain.usecase.GetNoteByIdUseCase
import edu.jgsilveira.tasks.kmp.domain.usecase.NewNoteUseCase
import edu.jgsilveira.tasks.kmp.domain.usecase.RemoveNoteUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.koin.dsl.module

val domainKoinModule = module {
    factory<NoteLocalDataSource> {
        NoteLocalDataSourceImpl(dao = get())
    }

    factory<NoteRepository> {
        NoteRepositoryImpl(localDataSource = get())
    }

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