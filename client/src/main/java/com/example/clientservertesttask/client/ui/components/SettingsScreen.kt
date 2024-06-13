package com.example.clientservertesttask.client.ui.components

import android.util.Patterns
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.clientservertesttask.client.R
import com.example.clientservertesttask.client.viewmodel.SettingsViewModel
import com.example.common.IpAddress

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    
    val ipAddress = viewModel.ipAddress.collectAsStateWithLifecycle()
    var hostText by remember(
        ipAddress.value
    ) {
        mutableStateOf(ipAddress.value.host)
    }
    var portText by remember(
        ipAddress.value
    ) {
        mutableStateOf(ipAddress.value.port.toString())
    }
    var hostErrorMessage by remember {
        mutableStateOf<String?>(null)
    }
    var portErrorMessage by remember {
        mutableStateOf<String?>(null)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.config))
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = stringResource(R.string.cd_navigate_back)
                        )
                    }
                }
            )
        },
        modifier = modifier.fillMaxSize()
    ) { paddingValues ->
        Column(
            verticalArrangement = Arrangement.spacedBy(
                space = 16.dp,
                alignment = Alignment.CenterVertically
            ),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(text = stringResource(R.string.host))
            TextField(
                value = hostText,
                onValueChange = {
                    hostText = it
                    if (hostErrorMessage != null) hostErrorMessage = null
                },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Decimal,
                    imeAction = ImeAction.Next
                ),
                singleLine = true,
                isError = hostErrorMessage != null,
                supportingText = {
                    if (hostErrorMessage != null) {
                        Text(text = hostErrorMessage!!)
                    }
                }
            )
            Text(text = stringResource(R.string.port))
            TextField(
                value = portText,
                onValueChange = {
                    portText = it
                    if (portErrorMessage != null) portErrorMessage = null
                },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                singleLine = true,
                isError = portErrorMessage != null,
                supportingText = {
                    if (portErrorMessage != null) {
                        Text(text = portErrorMessage!!)
                    }
                }
            )

            Button(
                onClick = {
                    try {
                        val port = portText.toInt()
                        var isInputValid = true
                        if (hostText.isEmpty()) {
                            isInputValid = false
                            hostErrorMessage = context.getString(R.string.enter_a_host)
                        } else if (!(Patterns.IP_ADDRESS.matcher(hostText).matches() || hostText == "0.0.0.0")) {
                            isInputValid = false
                            hostErrorMessage = context.getString(R.string.invalid_host_address)
                        }
                        if (portText.isEmpty()) {
                            isInputValid = false
                            portErrorMessage = context.getString(R.string.enter_a_host)
                        } else if (port !in 0..65535) {
                            isInputValid = false
                            portErrorMessage = context.getString(R.string.invalid_port)
                        }
                        if (isInputValid) {
                            viewModel.saveAddress(IpAddress(hostText, portText.toInt()))
                        }
                    } catch (e: NumberFormatException) {
                        portErrorMessage = context.getString(R.string.invalid_port)
                    }
                }
            ) {
                Text(text = stringResource(R.string.save))
            }
        }
    }
}