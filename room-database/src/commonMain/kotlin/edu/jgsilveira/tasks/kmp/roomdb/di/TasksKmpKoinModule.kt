package edu.jgsilveira.tasks.kmp.roomdb.di

import edu.jgsilveira.tasks.kmp.roomdb.dao.NoteDao
import edu.jgsilveira.tasks.kmp.roomdb.db.TasksKmpDatabase
import edu.jgsilveira.tasks.kmp.roomdb.db.getRoomDatabase
import org.koin.dsl.bind
import org.koin.dsl.module

val tasksKmpDatabaseKoinModule = module {
    single {
        getRoomDatabase(builder = get())
    } bind TasksKmpDatabase::class

    factory {
        get<TasksKmpDatabase>().provideNoteDao()
    } bind NoteDao::class
}