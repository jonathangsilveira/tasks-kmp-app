package edu.jgsilveira.tasks.kmp.roomdb.di

import androidx.room.Room
import androidx.room.RoomDatabase
import edu.jgsilveira.tasks.kmp.roomdb.db.DATABASE_NAME
import edu.jgsilveira.tasks.kmp.roomdb.db.TasksKmpDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

actual val roomDatabaseBuilderKoinModule = module {
    factory<RoomDatabase.Builder<TasksKmpDatabase>> {
        val appContext = androidContext().applicationContext
        val dbFile = appContext.getDatabasePath(DATABASE_NAME)
        Room.databaseBuilder<TasksKmpDatabase>(
            context = appContext,
            name = dbFile.absolutePath
        )
    }
}