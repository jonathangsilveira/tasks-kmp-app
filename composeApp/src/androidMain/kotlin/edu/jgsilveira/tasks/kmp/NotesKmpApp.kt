package edu.jgsilveira.tasks.kmp

import android.app.Application
import edu.jgsilveira.tasks.kmp.auth.di.domainAuthKoinModule
import edu.jgsilveira.tasks.kmp.di.domainKoinModule
import edu.jgsilveira.tasks.kmp.features.auth.signin.signInFeatureKoinModule
import edu.jgsilveira.tasks.kmp.features.auth.signup.signUpFeatureKoinModule
import edu.jgsilveira.tasks.kmp.features.notes.list.noteListKoinModule
import edu.jgsilveira.tasks.kmp.features.notes.manage.manageNoteKoinModule
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
                manageNoteKoinModule,
                domainAuthKoinModule,
                signUpFeatureKoinModule,
                signInFeatureKoinModule
            )
        }
    }
}