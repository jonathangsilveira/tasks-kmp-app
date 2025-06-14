package edu.jgsilveira.tasks.kmp

import android.app.Application
import edu.jgsilveira.tasks.kmp.di.domainKoinModule
import edu.jgsilveira.tasks.kmp.note.home.noteListKoinModule
import edu.jgsilveira.tasks.kmp.note.manageNoteKoinModule
import edu.jgsilveira.tasks.kmp.roomdb.di.roomDatabaseBuilderKoinModule
import edu.jgsilveira.tasks.kmp.roomdb.di.tasksKmpDatabaseKoinModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class NotesKmpApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@NotesKmpApp)
            modules(
                roomDatabaseBuilderKoinModule,
                tasksKmpDatabaseKoinModule,
                domainKoinModule,
                noteListKoinModule,
                manageNoteKoinModule
            )
        }
    }
}