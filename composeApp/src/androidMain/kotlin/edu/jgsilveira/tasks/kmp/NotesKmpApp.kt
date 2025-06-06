package edu.jgsilveira.tasks.kmp

import android.app.Application
import edu.jgsilveira.tasks.kmp.di.domainKoinModule
import edu.jgsilveira.tasks.kmp.note.manageNoteKoinModule
import org.koin.core.context.startKoin

class NotesKmpApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(
                domainKoinModule,
                manageNoteKoinModule
            )
        }
    }
}