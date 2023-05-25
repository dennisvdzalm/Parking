package nl.dennisvanderzalm.parking.shared.ui.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import nl.dennisvanderzalm.parking.shared.ui.Dependencies
import nl.dennisvanderzalm.parking.shared.ui.component.ParkingTopAppBar

@Composable
internal fun Login(onLoginComplete: () -> Unit) {
    val scope = rememberCoroutineScope()
    val viewModel = remember { LoginViewModel(scope, Dependencies.loginUseCase) }

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
