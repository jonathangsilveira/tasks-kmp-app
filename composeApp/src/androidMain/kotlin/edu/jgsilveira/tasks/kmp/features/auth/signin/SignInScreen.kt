package edu.jgsilveira.tasks.kmp.features.auth.signin

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import edu.jgsilveira.tasks.kmp.ui.composables.feedback.FeedbackScreenContent
import edu.jgsilveira.tasks.kmp.ui.composables.feedback.FeedbackContentType
import edu.jgsilveira.tasks.kmp.ui.composables.OutlinedTextField
import kotlinx.coroutines.flow.collectLatest
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.KoinContext
import taskskmpapp.composeapp.generated.resources.Res
import taskskmpapp.composeapp.generated.resources.email
import taskskmpapp.composeapp.generated.resources.generic_error_message
import taskskmpapp.composeapp.generated.resources.password
import taskskmpapp.composeapp.generated.resources.retry
import taskskmpapp.composeapp.generated.resources.signin
import taskskmpapp.composeapp.generated.resources.signup

@Composable
fun SignInScreen(
    modifier: Modifier = Modifier,
    navigateToSignUp: () -> Unit = {},
    navigateToHome: () -> Unit = {}
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    KoinContext {
        val viewModel = koinViewModel<SignInViewModel>()
        LaunchedEffect(viewModel.uiEffects) {
            lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiEffects.collectLatest { uiEffect ->
                    when (uiEffect) {
                        SignInUiEffect.NavigateToSignUp -> navigateToSignUp()
                        SignInUiEffect.NavigateToHome -> navigateToHome()
                        else -> Unit
                    }
                }
            }
        }
        Scaffold(
            modifier = modifier
        ) { paddingValues ->
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            SignInScreenContent(
                uiState = uiState,
                modifier = Modifier.fillMaxSize().padding(paddingValues),
                onUiAction = viewModel::dispatchAction
            )
        }
    }
}

@Composable
private fun SignInScreenContent(
    uiState: SignInUiState,
    modifier: Modifier = Modifier,
    onUiAction: (SignInUiAction) -> Unit = {}
) {
    when (uiState) {
        is SignInUiState.Error -> ErrorSignInScreenContent(
            messageResource = uiState.messageRes,
            modifier = modifier,
            onUiAction = onUiAction
        )
        SignInUiState.Initial -> FormSignInScreenContent(
            modifier = modifier,
            onUiAction = onUiAction
        )
        SignInUiState.Loading -> ProcessingSignInScreenContent(
            modifier = modifier
        )
    }
}

@Composable
private fun FormSignInScreenContent(
    modifier: Modifier = Modifier,
    email: String = "",
    password: String = "",
    onUiAction: (SignInUiAction) -> Unit = {}
) {
    val focusManager = LocalFocusManager.current
    var emailState by remember { mutableStateOf(email) }
    var passwordState by remember { mutableStateOf(password) }
    Column(
        modifier = modifier.padding(all = 16.dp),
        verticalArrangement = Arrangement.spacedBy(
            space = 8.dp,
            alignment = Alignment.CenterVertically
        )
    ) {
        OutlinedTextField(
            value = emailState,
            modifier = Modifier.fillMaxWidth().wrapContentHeight(),
            placeholder = stringResource(Res.string.email),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Email
            ),
            keyboardActions = KeyboardActions(
                onNext = {
                    focusManager.moveFocus(
                        focusDirection = FocusDirection.Next
                    )
                }
            ),
            onValueChange = { newValue ->
                emailState = newValue
            }
        )
        OutlinedTextField(
            value = passwordState,
            modifier = Modifier.fillMaxWidth().wrapContentHeight(),
            placeholder = stringResource(Res.string.password),
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Password
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    onUiAction(
                        SignInUiAction.SignInClick(
                            email = emailState,
                            password = passwordState
                        )
                    )
                }
            ),
            onValueChange = { newValue ->
                passwordState = newValue
            }
        )
        Button(
            modifier = Modifier.fillMaxWidth().wrapContentHeight(),
            onClick = {
                onUiAction(
                    SignInUiAction.SignInClick(
                        email = emailState,
                        password = passwordState
                    )
                )
            }
        ) {
            Text(text = stringResource(Res.string.signin))
        }
        TextButton(
            modifier = Modifier.fillMaxWidth().wrapContentHeight(),
            onClick = {
                onUiAction(SignInUiAction.SignUpClick)
            }
        ) {
            Text(text = stringResource(Res.string.signup))
        }
    }
}

@Composable
private fun ProcessingSignInScreenContent(
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
private fun ErrorSignInScreenContent(
    messageResource: StringResource,
    modifier: Modifier = Modifier,
    onUiAction: (SignInUiAction) -> Unit = {}
) {
    FeedbackScreenContent(
        messageText = stringResource(messageResource),
        primaryButtonText = stringResource(Res.string.retry),
        type = FeedbackContentType.ERROR,
        modifier = modifier,
        onPrimaryButtonClick = { onUiAction(SignInUiAction.RetrySignIn) }
    )
}

@Preview
@Composable
private fun FormSignInScreenContentPreview() {
    MaterialTheme {
        FormSignInScreenContent(
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Preview
@Composable
private fun ErrorSignInScreenContentPreview() {
    MaterialTheme {
        ErrorSignInScreenContent(
            messageResource = Res.string.generic_error_message,
            modifier = Modifier.fillMaxSize()
        )
    }
}