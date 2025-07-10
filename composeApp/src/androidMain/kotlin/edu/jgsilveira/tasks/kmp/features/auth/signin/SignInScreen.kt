package edu.jgsilveira.tasks.kmp.features.auth.signin

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
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
            modifier = modifier
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
    var emailState by remember { mutableStateOf(email) }
    var passwordState by remember { mutableStateOf(password) }
    Column(
        modifier = modifier.padding(all = 16.dp),
        verticalArrangement = Arrangement.spacedBy(space = 8.dp)
    ) {
        OutlinedTextField(
            value = emailState,
            modifier = Modifier.fillMaxWidth().wrapContentHeight(),
            placeholder = "E-mail",
            onValueChange = { newValue ->
                emailState = newValue
            }
        )
        OutlinedTextField(
            value = passwordState,
            modifier = Modifier.fillMaxWidth().wrapContentHeight(),
            placeholder = "Password",
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
            Text(text = "Sign in")
        }
        TextButton(
            modifier = Modifier.fillMaxWidth().wrapContentHeight(),
            onClick = {
                onUiAction(SignInUiAction.SignUpClick)
            }
        ) {
            Text(text = "Sign up")
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
    modifier: Modifier = Modifier
) {
    Box(modifier) {
        Text(
            text = stringResource(messageResource),
            modifier = Modifier.align(Alignment.Center)
        )
    }
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