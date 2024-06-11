package com.example.clientservertesttask.server.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.clientservertesttask.server.R
import com.example.clientservertesttask.server.viewmodel.MainViewModel

@Composable
fun MainScreen(
    onNavigate: (Destination) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = hiltViewModel()
) {
    val isServerStarted = viewModel.isServerStarted.collectAsStateWithLifecycle()

    Column(
        verticalArrangement = Arrangement.spacedBy(
            space = 16.dp,
            alignment = Alignment.CenterVertically
        ),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.padding(16.dp)
    ) {
        Button(
            onClick = {
                if (isServerStarted.value) {
                    viewModel.stopServer()
                } else {
                    viewModel.startServer()
                }
            },
            modifier = Modifier.fillMaxWidth(.6f)
        ) {
            Text(
                text = if (isServerStarted.value) {
                    stringResource(R.string.stop)
                } else {
                    stringResource(R.string.start)
                }
            )
        }

        Button(
            onClick = { onNavigate(Destination.Settings) }
        ) {
            Text(text = stringResource(R.string.config))
        }

        Button(
            onClick = { onNavigate(Destination.Logs) }
        ) {
            Text(text = stringResource(R.string.logs))
        }
    }
}