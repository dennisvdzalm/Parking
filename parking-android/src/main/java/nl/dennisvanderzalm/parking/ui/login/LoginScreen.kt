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
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import org.koin.androidx.compose.getViewModel

@Composable
@ExperimentalComposeUiApi
fun LoginScreen() {
    val viewModel: LoginViewModel = getViewModel()

    Scaffold(
        topBar = { TopAppBar { Text(text = "Parking") } },
        content = { LoginContent(viewModel::login) }
    )
}

@Composable
@ExperimentalComposeUiApi
fun LoginContent(onLogin: (username: String, password: String) -> Unit) {
    var username by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }

    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
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
                keyboardController?.hideSoftwareKeyboard()
                onLogin(username, password)
            }),
            onValueChange = { password = it },
            label = { Text(text = "Password") })

        Button(
            modifier = Modifier.padding(16.dp),
            onClick = { onLogin(username, password) }
        ) {
            Text(text = "Login")
        }
    }
}