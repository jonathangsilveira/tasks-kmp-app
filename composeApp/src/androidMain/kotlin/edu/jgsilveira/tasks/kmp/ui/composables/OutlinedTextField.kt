package edu.jgsilveira.tasks.kmp.ui.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.InputTransformation
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max

object TextFieldDefaults {
    val defaultPaddingDp = 16.dp
    val minHeightDp = 48.dp
    val maxHeightDp = 56.dp
}

@Composable
fun OutlinedTextField(
    value: String,
    modifier: Modifier = Modifier,
    minLines: Int = 1,
    maxLines: Int = Int.MAX_VALUE,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    placeholder: String? = null,
    onValueChange: (newValue: String) -> Unit = {}
) {
    Box(
        modifier = modifier
            .border(
                shape = RoundedCornerShape(size = 8.dp),
                border = BorderStroke(
                    width = 2.dp,
                    color = MaterialTheme.colors.onSurface
                )
            )
            .heightIn(
                min = TextFieldDefaults.minHeightDp,
                max = TextFieldDefaults.maxHeightDp
            )
            .padding(all = TextFieldDefaults.defaultPaddingDp)
    ) {
        if (!placeholder.isNullOrBlank() && value.isBlank()) {
            Text(
                text = placeholder,
                style = MaterialTheme.typography.caption,
                modifier = Modifier.align(Alignment.TopStart)
            )
        }
        BasicTextField(
            value = value,
            modifier = Modifier.wrapContentSize(),
            minLines = minLines,
            maxLines = maxLines,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            visualTransformation = visualTransformation,
            onValueChange = onValueChange
        )
    }
}

@Preview
@Composable
private fun OutlinedTextFieldWithPlaceholderPreview() {
    MaterialTheme {
        OutlinedTextField(
            value = "",
            placeholder = "Type some text...",
            modifier = Modifier.fillMaxWidth().wrapContentHeight()
        )
    }
}

@Preview
@Composable
private fun OutlinedTextFieldWithTextPreview() {
    MaterialTheme {
        OutlinedTextField(
            value = "Some text...",
            modifier = Modifier.fillMaxWidth().wrapContentHeight()
        )
    }
}

@Preview
@Composable
private fun OutlinedTextFieldAsPasswordPreview() {
    MaterialTheme {
        OutlinedTextField(
            value = "chablau",
            modifier = Modifier.fillMaxWidth().wrapContentHeight(),
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password
            )
        )
    }
}