package edu.jgsilveira.tasks.kmp.roomdb.di

import androidx.room.Room
import androidx.room.RoomDatabase
import edu.jgsilveira.tasks.kmp.roomdb.db.DATABASE_NAME
import edu.jgsilveira.tasks.kmp.roomdb.db.TasksKmpDatabase
import kotlinx.cinterop.ExperimentalForeignApi
import org.koin.dsl.module
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask

actual val roomDatabaseBuilderKoinModule = module {
    factory<RoomDatabase.Builder<TasksKmpDatabase>> {
        val dbFilePath = documentDirectory() + "/$DATABASE_NAME"
        Room.databaseBuilder<TasksKmpDatabase>(
            name = dbFilePath
        )
    }
}

@OptIn(ExperimentalForeignApi::class)
private fun documentDirectory(): String {
    val documentDirectory = NSFileManager.defaultManager.URLForDirectory(
        directory = NSDocumentDirectory,
        inDomain = NSUserDomainMask,
        appropriateForURL = null,
        create = false,
        error = null,
    )
    return requireNotNull(documentDirectory?.path)
}
