package edu.jgsilveira.tasks.kmp.ui.composables.feedback

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.stringResource
import taskskmpapp.composeapp.generated.resources.Res
import taskskmpapp.composeapp.generated.resources.generic_error_message
import taskskmpapp.composeapp.generated.resources.retry

@Composable
fun FeedbackContent(
    messageText: String,
    primaryButtonText: String,
    modifier: Modifier = Modifier,
    type: FeedbackContentType = FeedbackContentDefaults.DefaultType,
    titleText: String? = null,
    onPrimaryButtonClick: () -> Unit = {}
) {
    Column(
        modifier.padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(
            space = FeedbackContentDefaults.VerticalSpacing,
            alignment = Alignment.CenterVertically
        )
    ) {
        Box(
            modifier = Modifier.size(
                FeedbackContentDefaults.IconContainerSize
            ).background(
                color = type.containerColor,
                shape = CircleShape
            )
        ) {
            Icon(
                imageVector = type.iconImage,
                tint = type.iconTint,
                contentDescription = null,
                modifier = Modifier.size(FeedbackContentDefaults.IconSize)
                    .align(Alignment.Center)
            )
        }
        Spacer(
            modifier = Modifier.fillMaxWidth()
                .height(FeedbackContentDefaults.VerticalSpacing)
        )
        if (!titleText.isNullOrBlank()) {
            Text(
                text = titleText,
                modifier = Modifier.fillMaxWidth(),
                style = FeedbackContentDefaults.TitleStyle
            )
        }
        Text(
            text = messageText,
            modifier = Modifier.fillMaxWidth(),
            style = FeedbackContentDefaults.MessageStyle
        )
        Spacer(
            modifier = Modifier.fillMaxWidth()
                .height(FeedbackContentDefaults.VerticalSpacing)
        )
        TextButton(
            onClick = onPrimaryButtonClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = primaryButtonText)
        }
    }
}

@Preview(showSystemUi = false, showBackground = true)
@Composable
private fun ErrorFeedbackContentPreview() {
    MaterialTheme {
        FeedbackContent(
            titleText = stringResource(Res.string.generic_error_message),
            messageText = stringResource(Res.string.generic_error_message),
            primaryButtonText = stringResource(Res.string.retry),
            type = FeedbackContentType.ERROR,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Preview(showSystemUi = false, showBackground = true)
@Composable
private fun WarningFeedbackContentPreview() {
    MaterialTheme {
        FeedbackContent(
            titleText = stringResource(Res.string.generic_error_message),
            messageText = stringResource(Res.string.generic_error_message),
            primaryButtonText = stringResource(Res.string.retry),
            type = FeedbackContentType.WARNING,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Preview(showSystemUi = false, showBackground = true)
@Composable
private fun SuccessFeedbackContentPreview() {
    MaterialTheme {
        FeedbackContent(
            titleText = stringResource(Res.string.generic_error_message),
            messageText = stringResource(Res.string.generic_error_message),
            primaryButtonText = stringResource(Res.string.retry),
            type = FeedbackContentType.SUCCESS,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Preview(showSystemUi = false, showBackground = true)
@Composable
private fun InfoFeedbackContentPreview() {
    MaterialTheme {
        FeedbackContent(
            titleText = stringResource(Res.string.generic_error_message),
            messageText = stringResource(Res.string.generic_error_message),
            primaryButtonText = stringResource(Res.string.retry),
            modifier = Modifier.fillMaxSize()
        )
    }
}
