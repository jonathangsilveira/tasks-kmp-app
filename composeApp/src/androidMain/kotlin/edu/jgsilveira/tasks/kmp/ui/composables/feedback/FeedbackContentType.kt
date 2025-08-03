package edu.jgsilveira.tasks.kmp.ui.composables.feedback

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Done
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import edu.jgsilveira.tasks.kmp.ui.theme.ColorsDefault

enum class FeedbackContentType(
    val containerColor: Color,
    val iconImage: ImageVector,
    val iconTint: Color = Color.White
) {
    INFO(
        iconImage = Icons.Rounded.Info,
        containerColor = ColorsDefault.Blue300,
        iconTint = ColorsDefault.Blue700
    ),
    WARNING(
        iconImage = Icons.Rounded.Warning,
        containerColor = ColorsDefault.Amber300,
        iconTint = ColorsDefault.Amber700
    ),
    ERROR(
        iconImage = Icons.Rounded.Close,
        containerColor = ColorsDefault.Red300,
        iconTint = ColorsDefault.Red700
    ),
    SUCCESS(
        iconImage = Icons.Rounded.Done,
        containerColor = ColorsDefault.Green300,
        iconTint = ColorsDefault.Green700
    )
}
