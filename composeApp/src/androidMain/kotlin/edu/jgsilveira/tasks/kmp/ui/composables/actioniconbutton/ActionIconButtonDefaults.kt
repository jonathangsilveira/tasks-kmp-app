package edu.jgsilveira.tasks.kmp.ui.composables.actioniconbutton

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

object ActionIconButtonDefaults {
    val IconSize: Dp
        @Composable
        @ReadOnlyComposable
        get() = 24.dp

    val TintColor: Color
        @Composable
        @ReadOnlyComposable
        get() = MaterialTheme.colors.onPrimary
}