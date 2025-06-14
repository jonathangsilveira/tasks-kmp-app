package edu.jgsilveira.tasks.kmp.features.notes.manage

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Done
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import taskskmpapp.composeapp.generated.resources.Res
import taskskmpapp.composeapp.generated.resources.navigate_back_content_description
import taskskmpapp.composeapp.generated.resources.note
import taskskmpapp.composeapp.generated.resources.note_description_placeholder
import taskskmpapp.composeapp.generated.resources.note_save_content_description
import taskskmpapp.composeapp.generated.resources.note_title_placeholder

@Composable
internal fun ManageNoteScreen(
    modifier: Modifier = Modifier,
    viewModel: ManageNoteViewModel,
    onNavigateBackClick: () -> Unit = {}
) {
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()
    val keyboardController = LocalSoftwareKeyboardController.current
    Scaffold(
        modifier = modifier,
        scaffoldState = scaffoldState,
        topBar = {
            ManageNoteTopBar(
                modifier = Modifier.fillMaxWidth(),
                onNavigateIconClick = viewModel::navigateBack,
                onSaveClick = {
                    keyboardController?.hide()
                    viewModel.saveNote()
                }
            )
        },
    ) { innerPadding ->
        ConsumeUIState(viewModel.uiState) { uiState ->
            when (uiState) {
                is ManageNoteUIState.Content -> {
                    ManageNoteContent(
                        viewData = uiState.viewData,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        onTitleChange = { newTitle ->
                            viewModel.updateTitle(newTitle)
                        },
                        onDescriptionChange = { newDescription ->
                            viewModel.updateDescription(newDescription)
                        }
                    )
                }

                ManageNoteUIState.Initial -> {
                    Box(modifier = Modifier.fillMaxSize()) {
                        Text(
                            text = "Initial State",
                            modifier = Modifier.align(
                                Alignment.Center
                            )
                        )
                    }
                }
            }
        }
        ConsumeUIEffect(viewModel.uiEffects) { uiEffect ->
            when (uiEffect) {
                is ManageNoteUIEffect.ShowSnackBar -> {
                    coroutineScope.launch {
                        scaffoldState.snackbarHostState.showSnackbar(
                            message = "Note saved!",
                            duration = SnackbarDuration.Short
                        )
                    }
                }

                ManageNoteUIEffect.NavigateBack -> {
                    onNavigateBackClick()
                }
            }
        }
        val lifecycleOwner = LocalLifecycleOwner.current
        LaunchedEffect(Unit) {
            lifecycleOwner.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
                viewModel.getNote()
            }
        }
    }
}

@Composable
@Preview
private fun ManageNoteTopBar(
    modifier: Modifier = Modifier,
    onNavigateIconClick: () -> Unit = {},
    onSaveClick: () -> Unit = {}
) {
    TopAppBar(
        modifier = modifier,
        title = {
            Text(
                text = stringResource(
                    Res.string.note
                )
            )
        },
        navigationIcon = {
            IconButton(
                modifier = Modifier.wrapContentSize(),
                enabled = true,
                onClick = onNavigateIconClick
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                    modifier = Modifier.size(size = 24.dp),
                    contentDescription = stringResource(
                        Res.string.navigate_back_content_description
                    )
                )
            }
        },
        actions = {
            IconButton(
                modifier = Modifier.wrapContentSize(),
                enabled = true,
                onClick = onSaveClick
            ) {
                Icon(
                    imageVector = Icons.Rounded.Done,
                    modifier = Modifier.size(size = 24.dp),
                    contentDescription = stringResource(
                        Res.string.note_save_content_description
                    )
                )
            }
        }
    )
}

@Composable
internal fun ManageNoteContent(
    viewData: NoteChangesViewData,
    modifier: Modifier = Modifier,
    onTitleChange: (newTitle: String) -> Unit = {},
    onDescriptionChange: (newDescription: String) -> Unit = {}
) {
    val focusManager = LocalFocusManager.current
    Column(
        modifier,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        NoteField(
            value = viewData.title,
            placeholder = stringResource(Res.string.note_title_placeholder),
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = {
                    focusManager.moveFocus(
                        focusDirection = FocusDirection.Next
                    )
                }
            ),
            onValueChange = onTitleChange
        )
        NoteField(
            value = viewData.description.orEmpty(),
            placeholder = stringResource(
                Res.string.note_description_placeholder
            ),
            modifier = Modifier.fillMaxSize(),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Default
            ),
            onValueChange = onDescriptionChange
        )
    }
}

@Composable
private fun NoteField(
    value: String,
    modifier: Modifier = Modifier,
    minLines: Int = 1,
    maxLines: Int = Int.MAX_VALUE,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    placeholder: String? = null,
    onValueChange: (newValue: String) -> Unit = {}
) {

    val defaultPadding = 16.dp
    Box(modifier.padding(all = defaultPadding)) {
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
            onValueChange = onValueChange
        )
    }
}

@Composable
internal fun ConsumeUIState(
    uiStateFlow: StateFlow<ManageNoteUIState>,
    consumer: @Composable (ManageNoteUIState) -> Unit
) {
    val uiState by uiStateFlow.collectAsStateWithLifecycle()
    consumer(uiState)
}

@Composable
internal fun ConsumeUIEffect(
    uiEffects: Flow<ManageNoteUIEffect>,
    consumer: (ManageNoteUIEffect) -> Unit
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    LaunchedEffect(uiEffects) {
        lifecycleOwner.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
            uiEffects.collectLatest { consumer(it) }
        }
    }
}

@Composable
@Preview
internal fun ManageNoteContentPreview() {
    val viewData = NoteChangesViewData(
        title = "",
        description = ""
    )
    ManageNoteContent(
        viewData = viewData,
        modifier = Modifier.fillMaxSize()
    )
}

@Preview
@Composable
private fun NoteFieldPreview() {
    MaterialTheme {
        NoteField(
            value = "Valor",
            placeholder = "",
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 48.dp)
        )
    }
}
