package edu.jgsilveira.tasks.kmp.note.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.collectLatest
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import taskskmpapp.composeapp.generated.resources.Res
import taskskmpapp.composeapp.generated.resources.generic_error_message
import taskskmpapp.composeapp.generated.resources.navigate_back_content_description
import taskskmpapp.composeapp.generated.resources.note_add_contnet_description
import taskskmpapp.composeapp.generated.resources.notes_list_empty_state_message
import taskskmpapp.composeapp.generated.resources.notes_screen_title

@Composable
internal fun NoteListScreen(
    viewModel: NoteListViewModel,
    modifier: Modifier = Modifier,
    onNewNoteClick: () -> Unit = {},
    onNoteClick: (noteId: Long) -> Unit = {},
    onNavigationClick: () -> Unit = {}
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    LaunchedEffect(Unit) {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.fetchAllNotes()
        }
    }
    LaunchedEffect(viewModel.uiEffects) {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.uiEffects.collectLatest { uiEffect ->
                when (uiEffect) {
                    NoteListUIEffect.NavigateBack -> onNavigationClick()
                    NoteListUIEffect.NewNote -> onNewNoteClick()
                    is NoteListUIEffect.OpenNote -> onNoteClick(uiEffect.noteId)
                }
            }
        }
    }
    Scaffold(
        modifier = modifier,
        topBar = {
            NoteListTopBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                onNavigateIconClick = {
                    viewModel.navigateToPrevious()
                }
            )
        },
        floatingActionButton = {
            NewNoteFab {
                viewModel.newNote()
            }
        }
    ) { paddingValues ->
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()
        NoteListStateContent(
            uiState = uiState,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            onNoteClick = { note ->
                viewModel.openNote(note)
            }
        )
    }
}

@Composable
internal fun NoteListStateContent(
    uiState: NoteListUIState,
    modifier: Modifier = Modifier,
    onNoteClick: (item: NoteItemViewData) -> Unit
) {
    when (uiState) {
        is NoteListUIState.Content -> {
            val contentState = uiState as? NoteListUIState.Content
            contentState?.notes?.let { items ->
                NoteListContent(
                    items = items,
                    modifier = modifier,
                    onNoteClick = onNoteClick
                )
            }
        }

        NoteListUIState.Error -> NoteListErrorContent(
            messageRes = Res.string.generic_error_message,
            modifier = modifier
        )

        NoteListUIState.Empty -> NoteListEmptyContent(
            modifier = modifier
        )

        else -> NoteListLoadingContent(
            modifier = modifier
        )
    }
}

@Composable
internal fun NoteListLoadingContent(
    modifier: Modifier = Modifier
) {
    Box(modifier) {
        CircularProgressIndicator(
            modifier = Modifier
                .align(Alignment.Center)
                .height(48.dp),
        )
    }
}

@Composable
internal fun NoteListErrorContent(
    messageRes: StringResource,
    modifier: Modifier = Modifier
) {
    Box(modifier) {
        Text(
            text = stringResource(messageRes),
            style = MaterialTheme.typography.h4,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
internal fun NoteListContent(
    items: List<NoteItemViewData>,
    modifier: Modifier = Modifier,
    onNoteClick: (item: NoteItemViewData) -> Unit
) {
    val lazyListState = rememberLazyListState()
    LazyColumn(
        modifier = modifier,
        state = lazyListState
    ) {
        itemsIndexed(
            items = items,
            key = { _, note -> note.noteId },
            contentType = { _, note -> note }
        ) { index, note ->
            NoteListItem(
                viewData = note,
                onClick = {
                    onNoteClick(note)
                }
            )
            if (index < items.lastIndex) {
                Divider(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                    color = MaterialTheme.colors.onSurface,
                    thickness = 1.dp
                )
            }
        }
    }
}

@Composable
private fun NoteListItem(
    viewData: NoteItemViewData,
    onClick: () -> Unit = {}
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 48.dp)
            .clickable(onClick = onClick)
            .padding(
                vertical = 8.dp,
                horizontal = 16.dp
            )
    ) {
        Text(text = viewData.title)
        Text(
            text = viewData.description.orEmpty(),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
private fun NoteListEmptyContent(
    modifier: Modifier = Modifier
) {
    Box(modifier) {
        Text(
            text = stringResource(
                Res.string.notes_list_empty_state_message
            ),
            style = MaterialTheme.typography.h6,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .padding(vertical = 20.dp)
                .align(Alignment.TopCenter)
        )
    }
}

@Composable
private fun NoteListTopBar(
    modifier: Modifier = Modifier,
    onNavigateIconClick: () -> Unit = {}
) {
    TopAppBar(
        modifier = modifier,
        title = {
            val title = stringResource(Res.string.notes_screen_title)
            Text(text = title)
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
        }
    )
}

@Composable
private fun NewNoteFab(
    onClick: () -> Unit
) {
    FloatingActionButton(
        modifier = Modifier.size(48.dp),
        shape = RoundedCornerShape(size = 12.dp),
        onClick = onClick
    ) {
        Icon(
            imageVector = Icons.Filled.Add,
            tint = MaterialTheme.colors.onSecondary,
            contentDescription = stringResource(
                Res.string.note_add_contnet_description
            )
        )
    }
}

@Preview
@Composable
internal fun NoteListContentPreview() {
    MaterialTheme {
        NoteListContent(
            modifier = Modifier.fillMaxSize(),
            onNoteClick = {},
            items = List(5) { index ->
                NoteItemViewData(
                    noteId = index.toLong(),
                    title = "Title #$index",
                    description = "Description #$index"
                )
            }
        )
    }
}

@Preview
@Composable
internal fun NewNoteFabPreview() {
    MaterialTheme {
        NewNoteFab(onClick = {})
    }
}

@Preview
@Composable
internal fun NoteListTopBarPreview() {
    MaterialTheme {
        NoteListTopBar(
            modifier = Modifier.fillMaxWidth(),
            onNavigateIconClick = {}
        )
    }
}