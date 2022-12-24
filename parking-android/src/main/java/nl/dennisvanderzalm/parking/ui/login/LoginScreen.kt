package nl.dennisvanderzalm.parking.ui.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusOrder
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import nl.dennisvanderzalm.parking.ui.component.ParkingTopAppBar
import org.koin.androidx.compose.getViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun LoginScreen(onLoginComplete: () -> Unit) {
    val viewModel: LoginViewModel = koinViewModel()

    Scaffold(
        topBar = { ParkingTopAppBar(title = "Login") },
        content = { paddingValues ->
            LoginContent(
                modifier = Modifier.padding(paddingValues),
                state = viewModel.state,
                onLoginComplete = onLoginComplete,
                onInputComplete = viewModel::login
            )
        }
    )
}

@Composable
private fun LoginContent(
    modifier: Modifier = Modifier,
    state: LoginViewState,
    onLoginComplete: () -> Unit,
    onInputComplete: (username: String, password: String) -> Unit
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        when (state) {
            is LoginViewState.EnterCredentials -> CredentialsInput(onInputComplete)
            is LoginViewState.Loading -> CircularProgressIndicator()
            is LoginViewState.LoginSuccessFull -> onLoginComplete()
            is LoginViewState.LoginError -> CredentialsInput(onInputComplete)
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun CredentialsInput(onInputComplete: (username: String, password: String) -> Unit) {
    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current

    var username by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }

    OutlinedTextField(
        modifier = Modifier
            .padding(16.dp),
        value = username,
        maxLines = 1,
        keyboardOptions = KeyboardOptions(
            autoCorrect = false,
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Next
        ),
        keyboardActions = KeyboardActions(onNext = { focusRequester.requestFocus() }),
        onValueChange = { username = it },
        label = { Text(text = "Username") }
    )

    OutlinedTextField(
        modifier = Modifier
            .focusRequester(focusRequester)
            .padding(16.dp),
        value = password,
        visualTransformation = PasswordVisualTransformation(),
        maxLines = 1,
        keyboardOptions = KeyboardOptions(
            autoCorrect = false,
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(onDone = {
            keyboardController?.hide()
            onInputComplete(username, password)
        }),
        onValueChange = { password = it },
        label = { Text(text = "Password") })

    Button(
        modifier = Modifier.padding(16.dp),
        onClick = { onInputComplete(username, password) }
    ) {
        Text(text = "Login")
    }
}
