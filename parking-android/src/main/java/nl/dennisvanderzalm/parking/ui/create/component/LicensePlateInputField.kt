package nl.dennisvanderzalm.parking.ui.create.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import nl.dennisvanderzalm.parking.shared.core.model.DutchLicensePlateNumber
import nl.dennisvanderzalm.parking.shared.core.model.isLicensePlate
import nl.dennisvanderzalm.parking.shared.core.model.toLicensePlateNumber
import nl.dennisvanderzalm.parking.ui.create.AddressBookViewState
import nl.dennisvanderzalm.parking.ui.theme.ParkingTheme

@Composable
fun LicensePlateInputField(
    state: AddressBookViewState,
    onUpdateSearchQuery: (String) -> Unit,
    onSelectLicensePlate: (DutchLicensePlateNumber?) -> Unit,
    modifier: Modifier = Modifier,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
) {
    val focusManager = LocalFocusManager.current
    var licensePlateText by rememberSaveable { mutableStateOf("") }

    LaunchedEffect(licensePlateText) {
        onUpdateSearchQuery(licensePlateText)

        if (licensePlateText.isLicensePlate()) {
            val plate = licensePlateText.toLicensePlateNumber()
            licensePlateText = plate.prettyNumber.uppercase()
            onSelectLicensePlate(licensePlateText.toLicensePlateNumber())
            focusManager.clearFocus()
        }
    }

    OutlinedTextField(
        modifier = modifier.padding(16.dp),
        value = licensePlateText.uppercase(),
        maxLines = 1,
        keyboardOptions = KeyboardOptions(
            autoCorrect = false,
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Next
        ),
        trailingIcon = {
            if (state.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(16.dp),
                    strokeWidth = 2.dp
                )
            } else if (state.selectedEntry != null) {
                Icon(
                    modifier = Modifier.clickable(
                        indication = rememberRipple(bounded = false),
                        enabled = true,
                        interactionSource = remember { MutableInteractionSource() },
                        onClick = {
                            licensePlateText = ""
                            onSelectLicensePlate(null)
                        }
                    ),
                    imageVector = Icons.Default.Clear,
                    contentDescription = "Clear license input"
                )
            }
        },
        onValueChange = { licensePlateText = it },
        keyboardActions = keyboardActions,
        label = { Text(text = "License plate") }
    )

    AnimatedVisibility(visible = state.showSuggestions) {
        LazyColumn(horizontalAlignment = Alignment.CenterHorizontally) {
            items(state.searchSuggestions) { item ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { licensePlateText = item.licensePlateNumber.prettyNumber }
                ) {
                    Text(
                        modifier = Modifier
                            .padding(8.dp)
                            .weight(1f),
                        text = item.name,
                        textAlign = TextAlign.Start,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        modifier = Modifier
                            .padding(8.dp)
                            .weight(1f),
                        maxLines = 1,
                        textAlign = TextAlign.End,
                        text = item.licensePlateNumber.prettyNumber,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    ParkingTheme {
        LicensePlateInputField(
            state = AddressBookViewState(),
            onUpdateSearchQuery = {},
            onSelectLicensePlate = {})
    }
}