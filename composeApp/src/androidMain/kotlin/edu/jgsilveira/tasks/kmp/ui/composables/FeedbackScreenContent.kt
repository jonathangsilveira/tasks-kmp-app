package edu.jgsilveira.tasks.kmp.ui.composables

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Done
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import edu.jgsilveira.tasks.kmp.ui.theme.ColorsDefault
import org.jetbrains.compose.resources.stringResource
import taskskmpapp.composeapp.generated.resources.Res
import taskskmpapp.composeapp.generated.resources.generic_error_message
import taskskmpapp.composeapp.generated.resources.retry

enum class FeedbackScreenContentType(
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

object FeedbackScreenContentDefaults {
    val DefaultType = FeedbackScreenContentType.INFO

    val VerticalSpacing = 8.dp
    val IconContainerSize = 72.dp
    val IconSize = 24.dp

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

@Composable
fun FeedbackScreenContent(
    messageText: String,
    primaryButtonText: String,
    modifier: Modifier = Modifier,
    type: FeedbackScreenContentType = FeedbackScreenContentDefaults.DefaultType,
    titleText: String? = null,
    onPrimaryButtonClick: () -> Unit = {}
) {
    Column(
        modifier.padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(
            space = FeedbackScreenContentDefaults.VerticalSpacing,
            alignment = Alignment.CenterVertically
        )
    ) {
        Box(
            modifier = Modifier.size(
                FeedbackScreenContentDefaults.IconContainerSize
            ).background(
                color = type.containerColor,
                shape = CircleShape
            )
        ) {
            Icon(
                imageVector = type.iconImage,
                tint = type.iconTint,
                contentDescription = null,
                modifier = Modifier.size(FeedbackScreenContentDefaults.IconSize)
                    .align(Alignment.Center)
            )
        }
        Spacer(
            modifier = Modifier.fillMaxWidth()
                .height(FeedbackScreenContentDefaults.VerticalSpacing)
        )
        if (!titleText.isNullOrBlank()) {
            Text(
                text = titleText,
                modifier = Modifier.fillMaxWidth(),
                style = FeedbackScreenContentDefaults.TitleStyle
            )
        }
        Text(
            text = messageText,
            modifier = Modifier.fillMaxWidth(),
            style = FeedbackScreenContentDefaults.MessageStyle
        )
        Spacer(
            modifier = Modifier.fillMaxWidth()
                .height(FeedbackScreenContentDefaults.VerticalSpacing)
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
private fun ErrorFeedbackScreenContentPreview() {
    MaterialTheme {
        FeedbackScreenContent(
            titleText = stringResource(Res.string.generic_error_message),
            messageText = stringResource(Res.string.generic_error_message),
            primaryButtonText = stringResource(Res.string.retry),
            type = FeedbackScreenContentType.ERROR,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Preview(showSystemUi = false, showBackground = true)
@Composable
private fun WarningFeedbackScreenContentPreview() {
    MaterialTheme {
        FeedbackScreenContent(
            titleText = stringResource(Res.string.generic_error_message),
            messageText = stringResource(Res.string.generic_error_message),
            primaryButtonText = stringResource(Res.string.retry),
            type = FeedbackScreenContentType.WARNING,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Preview(showSystemUi = false, showBackground = true)
@Composable
private fun SuccessFeedbackScreenContentPreview() {
    MaterialTheme {
        FeedbackScreenContent(
            titleText = stringResource(Res.string.generic_error_message),
            messageText = stringResource(Res.string.generic_error_message),
            primaryButtonText = stringResource(Res.string.retry),
            type = FeedbackScreenContentType.SUCCESS,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Preview(showSystemUi = false, showBackground = true)
@Composable
private fun InfoFeedbackScreenContentPreview() {
    MaterialTheme {
        FeedbackScreenContent(
            titleText = stringResource(Res.string.generic_error_message),
            messageText = stringResource(Res.string.generic_error_message),
            primaryButtonText = stringResource(Res.string.retry),
            modifier = Modifier.fillMaxSize()
        )
    }
}
