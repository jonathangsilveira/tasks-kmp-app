package edu.jgsilveira.tasks.kmp.ui.composables.actioniconbutton

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Done
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import edu.jgsilveira.tasks.kmp.ui.theme.ColorsDefault

@Composable
fun ActionIconButton(
    imageVector: ImageVector,
    iconContentDescription: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    iconSize: Dp = ActionIconButtonDefaults.IconSize,
    iconTint: Color = ActionIconButtonDefaults.TintColor,
    onClick: () -> Unit = {}
) {
    IconButton(
        modifier = modifier,
        enabled = enabled,
        onClick = onClick
    ) {
        Icon(
            imageVector = imageVector,
            contentDescription = iconContentDescription,
            modifier = Modifier.size(size = iconSize),
            tint = iconTint
        )
    }
}

@Preview
@Composable
private fun ActionIconButtonPreview() {
    MaterialTheme {
        ActionIconButton(
            modifier = Modifier.wrapContentSize(),
            imageVector = Icons.Rounded.Done,
            iconContentDescription = "Done"
        )
    }
}

@Preview
@Composable
private fun ActionIconButtonWithTintPreview() {
    MaterialTheme {
        ActionIconButton(
            modifier = Modifier.wrapContentSize(),
            imageVector = Icons.Rounded.Close,
            iconContentDescription = "Close",
            iconTint = ColorsDefault.Red500
        )
    }
}