package edu.jgsilveira.tasks.kmp.ui.composables.feedback

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

object FeedbackContentDefaults {
    val DefaultType = FeedbackContentType.INFO

    val VerticalSpacing = 8.dp
    val IconContainerSize = 72.dp
    val IconSize = 32.dp

    val TitleStyle: TextStyle
        @Composable
        @ReadOnlyComposable
        get() = MaterialTheme.typography.h5.copy(
            textAlign = TextAlign.Center
        )

    val MessageStyle: TextStyle
        @Composable
        @ReadOnlyComposable
        get() = MaterialTheme.typography.body1.copy(
            textAlign = TextAlign.Center
        )
}