package edu.jgsilveira.tasks.kmp

import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import edu.jgsilveira.tasks.kmp.domain.model.NEW_NOTE_ID
import edu.jgsilveira.tasks.kmp.features.auth.signin.SignInScreen
import edu.jgsilveira.tasks.kmp.features.auth.signup.SignUpScreen
import edu.jgsilveira.tasks.kmp.navigation.NotesAppNavScreen
import edu.jgsilveira.tasks.kmp.features.notes.manage.ManageNoteScreen
import edu.jgsilveira.tasks.kmp.features.notes.manage.ManageNoteViewModel
import edu.jgsilveira.tasks.kmp.features.notes.list.NoteListScreen
import edu.jgsilveira.tasks.kmp.features.notes.list.NoteListViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
@Preview
fun App() {
    MaterialTheme {
        val activity = LocalActivity.current
        val navHostController = rememberNavController()
        NavHost(
            navController = navHostController,
            startDestination = NotesAppNavScreen.SignIn,
            modifier = Modifier.fillMaxSize()
                .windowInsetsPadding(WindowInsets.statusBars)
        ) {
            composable<NotesAppNavScreen.NoteListScreen> {
                val noteListViewModel = koinViewModel<NoteListViewModel>()
                NoteListScreen(
                    viewModel = noteListViewModel,
                    modifier = Modifier.fillMaxSize(),
                    onNewNoteClick = {
                        navHostController.navigate(
                            route = NotesAppNavScreen.ManageNote(
                                id = NEW_NOTE_ID
                            )
                        )
                    },
                    onNoteClick = { noteId ->
                        navHostController.navigate(
                            route = NotesAppNavScreen.ManageNote(
                                id = noteId
                            )
                        )
                    },
                    onNavigationClick = {
                        activity?.finish()
                    }
                )
            }
            composable<NotesAppNavScreen.ManageNote> { backStackEntry ->
                val manageNote = backStackEntry.toRoute<NotesAppNavScreen.ManageNote>()
                val manageNoteViewModel = koinViewModel<ManageNoteViewModel> {
                    parametersOf(manageNote.id)
                }
                ManageNoteScreen(
                    viewModel = manageNoteViewModel,
                    modifier = Modifier.fillMaxSize(),
                    onNavigateBackClick = {
                        navHostController.navigateUp()
                    }
                )
            }

            composable<NotesAppNavScreen.SignUp> {
                SignUpScreen(
                    modifier = Modifier.fillMaxSize(),
                    navigateUp = {
                        navHostController.navigate(
                            route = NotesAppNavScreen.NoteListScreen
                        )
                    }
                )
            }

            composable<NotesAppNavScreen.SignIn> {
                SignInScreen(
                    modifier = Modifier.fillMaxSize(),
                    navigateToHome = {
                        navHostController.navigate(
                            route = NotesAppNavScreen.NoteListScreen
                        )
                    },
                    navigateToSignUp = {
                        navHostController.navigate(
                            route = NotesAppNavScreen.SignUp
                        )
                    }
                )
            }
        }
    }
}
