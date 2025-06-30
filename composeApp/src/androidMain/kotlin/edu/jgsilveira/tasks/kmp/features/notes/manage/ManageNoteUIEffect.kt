package edu.jgsilveira.tasks.kmp.features.notes.manage

import org.jetbrains.compose.resources.StringResource

internal interface ManageNoteUIEffect {
    data class ShowSnackBar(
        val messageRes: StringResource
    ) : ManageNoteUIEffect
    data object NavigateBack : ManageNoteUIEffect
}