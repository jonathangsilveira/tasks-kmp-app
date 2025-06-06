package edu.jgsilveira.tasks.kmp

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import edu.jgsilveira.tasks.kmp.note.ManageNoteScreen
import edu.jgsilveira.tasks.kmp.note.ManageNoteViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
@Preview
fun App() {
    MaterialTheme {
        val manageNoteViewModel = koinViewModel<ManageNoteViewModel> {
            parametersOf(0L)
        }
        ManageNoteScreen(
            viewModel = manageNoteViewModel,
            modifier = Modifier.fillMaxSize()
                .windowInsetsPadding(
                    WindowInsets.statusBars
                ),
            onNavigateBackClick = {
                print("Navigation back clicked")
            }
        )
    }
}
