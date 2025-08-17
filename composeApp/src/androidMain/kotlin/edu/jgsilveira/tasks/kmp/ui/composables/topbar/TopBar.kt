package edu.jgsilveira.tasks.kmp.ui.composables.topbar

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Share
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import edu.jgsilveira.tasks.kmp.ui.composables.actioniconbutton.ActionIconButton
import org.jetbrains.compose.resources.stringResource
import taskskmpapp.composeapp.generated.resources.Res
import taskskmpapp.composeapp.generated.resources.navigate_back_content_description

@Composable
fun TopBar(
    modifier: Modifier = Modifier,
    title: @Composable () -> Unit = {},
    navigationIcon: @Composable () -> Unit = {},
    actionIcons: @Composable RowScope.() -> Unit = {}
) {
    TopAppBar(
        modifier = modifier,
        title = title,
        navigationIcon = navigationIcon,
        actions = { actionIcons() }
    )
}

@Preview
@Composable
private fun TopBarWithTitlePreview() {
    MaterialTheme {
        TopBar(
            title = {
                Text(text = "Title")
            },
            modifier = Modifier.fillMaxWidth().wrapContentHeight()
        )
    }
}

@Preview
@Composable
private fun TopBarWithTitleAndNavIconPreview() {
    MaterialTheme {
        TopBar(
            title = {
                Text(text = "Title")
            },
            navigationIcon = {
                ActionIconButton(
                    imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                    iconContentDescription = "Click to navigate for previous screen"
                )
            },
            modifier = Modifier.fillMaxWidth().wrapContentHeight()
        )
    }
}

@Preview
@Composable
private fun TopBarWithActionIconsPreview() {
    MaterialTheme {
        TopBar(
            title = {
                Text(text = "Title")
            },
            actionIcons = {
                ActionIconButton(
                    imageVector = Icons.Rounded.Share,
                    iconContentDescription = "Click to send reminder to a friend"
                )
                ActionIconButton(
                    imageVector = Icons.Rounded.Delete,
                    iconContentDescription = "Click to delete this note"
                )
            },
            modifier = Modifier.fillMaxWidth().wrapContentHeight()
        )
    }
}

@Preview
@Composable
private fun TopBarWithoutTitlePreview() {
    MaterialTheme {
        TopBar(
            navigationIcon = {
                ActionIconButton(
                    imageVector = Icons.AutoMirrored.Default.ArrowBack,
                    iconContentDescription = "Click to navigate for previous screen"
                )
            },
            actionIcons = {
                ActionIconButton(
                    imageVector = Icons.Rounded.Delete,
                    iconContentDescription = "Click to delete this note"
                )
            },
            modifier = Modifier.fillMaxWidth().wrapContentHeight()
        )
    }
}

@Preview
@Composable
private fun TopBarEmptyPreview() {
    MaterialTheme {
        TopBar(modifier = Modifier.fillMaxWidth().wrapContentHeight())
    }
}