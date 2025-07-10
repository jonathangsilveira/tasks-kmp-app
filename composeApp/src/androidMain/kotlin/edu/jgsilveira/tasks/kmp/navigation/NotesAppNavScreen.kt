package edu.jgsilveira.tasks.kmp.navigation

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

internal sealed interface NotesAppNavScreen {
    @Serializable
    @SerialName("notes_list")
    data object NoteListScreen : NotesAppNavScreen

    @Serializable
    @SerialName("manage_note")
    data class ManageNote(
        val id: Long
    ) : NotesAppNavScreen

    @Serializable
    @SerialName("signup")
    data object SignUp : NotesAppNavScreen

    @Serializable
    @SerialName("signin")
    data object SignIn : NotesAppNavScreen
}