package edu.jgsilveira.tasks.kmp.note

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.Text
import androidx.compose.material.TextField
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
    Scaffold(
        modifier = modifier,
        scaffoldState = scaffoldState,
        topBar = {
            ManageNoteTopBar(
                modifier = Modifier.fillMaxWidth(),
                onNavigateIconClick = viewModel::navigateBack,
                onSaveClick = viewModel::saveNote
            )
        },
    ) { innerPadding ->
        ConsumeUIState(viewModel.uiState) { uiState ->
            when (uiState) {
                is ManageNoteUIState.Content -> {
                    ManageNoteContent(
                        viewData = uiState.viewData,
                        onTitleChange = { newTitle ->
                            viewModel.updateTitle(newTitle)
                        },
                        onDescriptionChange = { newDescription ->
                            viewModel.updateDescription(newDescription)
                        },
                        modifier = Modifier.fillMaxSize()
                            .padding(innerPadding)
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
                    /*val message = stringResource(
                        uiEffect.messageRes
                    )*/
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
                modifier = Modifier.size(24.dp),
                enabled = true,
                onClick = onNavigateIconClick
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                    modifier = Modifier.fillMaxSize(),
                    contentDescription = stringResource(
                        Res.string.navigate_back_content_description
                    )
                )
            }
        },
        actions = {
            IconButton(
                modifier = Modifier.size(24.dp),
                enabled = true,
                onClick = onSaveClick
            ) {
                Icon(
                    imageVector = Icons.Rounded.Done,
                    modifier = Modifier.fillMaxSize(),
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
    onTitleChange: ((newTitle: String) -> Unit)? = null,
    onDescriptionChange: ((newDescription: String) -> Unit)? = null
) {
    Column(
        modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        TextField(
            value = viewData.title,
            onValueChange = { newTitle ->
                onTitleChange?.invoke(newTitle)
            },
            placeholder = {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(
                        Res.string.note_title_placeholder
                    )
                )
            },
            maxLines = 1,
            modifier = Modifier.fillMaxWidth()
                .padding(horizontal = 8.dp)
        )
        TextField(
            value = viewData.description.orEmpty(),
            onValueChange = { newDescription ->
                onDescriptionChange?.invoke(newDescription)
            },
            placeholder = {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(
                        Res.string.note_description_placeholder
                    )
                )
            },
            maxLines = 3,
            modifier = Modifier.fillMaxWidth()
                .padding(horizontal = 8.dp)
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