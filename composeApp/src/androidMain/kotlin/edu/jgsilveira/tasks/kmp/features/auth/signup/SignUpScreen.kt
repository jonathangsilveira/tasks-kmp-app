package edu.jgsilveira.tasks.kmp.features.auth.signup

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
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
import edu.jgsilveira.tasks.kmp.ui.composables.OutlinedTextField
import kotlinx.coroutines.flow.collectLatest
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.KoinContext
import taskskmpapp.composeapp.generated.resources.Res
import taskskmpapp.composeapp.generated.resources.email
import taskskmpapp.composeapp.generated.resources.full_name
import taskskmpapp.composeapp.generated.resources.password
import taskskmpapp.composeapp.generated.resources.register
import taskskmpapp.composeapp.generated.resources.retry
import taskskmpapp.composeapp.generated.resources.signup_success_message

@Composable
fun SignUpScreen(
    modifier: Modifier = Modifier,
    navigateUp: () -> Unit = {}
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    KoinContext {
        val viewModel = koinViewModel<SignUpViewModel>()
        LaunchedEffect(viewModel.uiEffects) {
            lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiEffects.collectLatest { uiEffect ->
                    when (uiEffect) {
                        SignUpUiEffect.NavigateUp -> navigateUp()
                        else -> Unit
                    }
                }
            }
        }
        Scaffold(
            modifier = modifier
        ) { paddingValues ->
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            SignUpScreenContent(
                uiState = uiState,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                onUiAction = viewModel::dispatchAction
            )
        }
    }
}

@Composable
internal fun SignUpScreenContent(
    uiState: SignUpUiState,
    modifier: Modifier = Modifier,
    onUiAction: (SignUpUiAction) -> Unit = {}
) {
    when (uiState) {
        is SignUpUiState.Error -> {
            ErrorSignUpScreenContent(
                messageResource = uiState.messageRes,
                modifier = modifier
            )
        }
        SignUpUiState.Initial -> FormSignUpScreenContent(
            modifier = modifier,
            onUiAction = onUiAction
        )
        SignUpUiState.Processing -> {
            ProcessingSignUpScreenContent(modifier)
        }
        SignUpUiState.Success -> {
            SuccessSignUpScreenContent(modifier)
        }
    }
}

@Composable
private fun FormSignUpScreenContent(
    modifier: Modifier = Modifier,
    fullName: String = "",
    email: String = "",
    password: String = "",
    onUiAction: (SignUpUiAction) -> Unit = {}
) {
    val focusManager = LocalFocusManager.current
    var fullNameState by remember { mutableStateOf(fullName) }
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
            value = fullNameState,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            placeholder = stringResource(Res.string.full_name),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Text
            ),
            keyboardActions = KeyboardActions(
                onNext = {
                    focusManager.moveFocus(
                        focusDirection = FocusDirection.Next
                    )
                }
            ),
            onValueChange = { newValue ->
                fullNameState = newValue
            }
        )
        OutlinedTextField(
            value = emailState,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
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
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            placeholder = stringResource(Res.string.password),
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Send,
                keyboardType = KeyboardType.Password
            ),
            keyboardActions = KeyboardActions(
                onSend = {
                    onUiAction(
                        SignUpUiAction.SignUpButtonClick(
                            fullName = fullNameState,
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
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            onClick = {
                onUiAction(
                    SignUpUiAction.SignUpButtonClick(
                        fullName = fullNameState,
                        email = emailState,
                        password = passwordState
                    )
                )
            }
        ) {
            Text(text = stringResource(Res.string.register))
        }
    }
}

@Composable
private fun ProcessingSignUpScreenContent(
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
private fun SuccessSignUpScreenContent(
    modifier: Modifier = Modifier
) {
    Box(modifier) {
        Text(
            text = stringResource(Res.string.signup_success_message),
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
private fun ErrorSignUpScreenContent(
    messageResource: StringResource,
    modifier: Modifier = Modifier,
    onUiAction: (SignUpUiAction) -> Unit = {}
) {
    Box(modifier, contentAlignment = Alignment.Center) {
        Column(
            modifier = Modifier.wrapContentSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(messageResource)
            )
            TextButton(
                onClick = { onUiAction(SignUpUiAction.RetrySignUp) }
            ) {
                Text(text = stringResource(Res.string.retry))
            }
        }
    }
}

@Preview
@Composable
private fun FormSignUpScreenContentPreview() {
    MaterialTheme {
        FormSignUpScreenContent(
            modifier = Modifier.fillMaxSize()
        )
    }
}