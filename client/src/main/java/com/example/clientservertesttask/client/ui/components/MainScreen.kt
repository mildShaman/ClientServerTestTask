package com.example.clientservertesttask.client.ui.components

import android.icu.util.Calendar
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.clientservertesttask.client.R
import com.example.clientservertesttask.client.viewmodel.MainViewModel

@Composable
fun MainScreen(
    onNavigate: (Destination) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val isClientActive = viewModel.isClientActive.collectAsStateWithLifecycle()

    Column(
        verticalArrangement = Arrangement.spacedBy(
            space = 16.dp,
            alignment = Alignment.CenterVertically
        ),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize()
            .padding(16.dp)
    ) {
        Button(
            onClick = {
                if (isClientActive.value) {
                    viewModel.disconnectFromServer()
                } else {
                    viewModel.connectToServer()
                }
            },
            modifier = Modifier.fillMaxWidth(.6f)
        ) {
            Text(
                text = if (isClientActive.value) {
                    stringResource(R.string.stop)
                } else {
                    stringResource(R.string.start)
                }
            )
        }

        Button(
            onClick = { onNavigate(Destination.Settings) },
            modifier = Modifier.fillMaxWidth(.6f)
        ) {
            Text(text = stringResource(R.string.config))
        }
    }

    if (isClientActive.value) {
        val url = "https://developers.android.com"
        val intent = CustomTabsIntent.Builder().build()
        intent.launchUrl(context, Uri.parse(url))
        viewModel.onBrowserOpen(Calendar.getInstance().timeInMillis)
    }
}