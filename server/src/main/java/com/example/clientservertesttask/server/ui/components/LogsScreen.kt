package com.example.clientservertesttask.server.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.clientservertesttask.server.R
import com.example.clientservertesttask.server.toDateString
import com.example.clientservertesttask.server.ui.theme.ClientServerTestTaskTheme
import com.example.clientservertesttask.server.viewmodel.LogsViewModel
import com.example.common.Gesture
import com.example.common.GestureResult

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LogsScreen(
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: LogsViewModel = hiltViewModel()
) {
    val logs = viewModel.logs.collectAsStateWithLifecycle()

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
        LazyColumn(
            modifier = Modifier.padding(paddingValues)
        ) {
            items(logs.value) {
                LogItem(it)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LogItem(
    data: GestureResult,
    modifier: Modifier = Modifier
) {
    var expanded by remember {
        mutableStateOf(false)
    }

    Card(
        onClick = { expanded = !expanded },
        modifier = modifier.padding(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = stringResource(R.string.start_time_prefix) + data.startTime.toDateString()
                )
                AnimatedVisibility(visible = expanded) {
                    Column {
                        Text(
                            text = stringResource(R.string.end_time_prefix) + data.endTime.toDateString()
                        )
                        Text(
                            text = stringResource(R.string.start_x_prefix) + "${data.gestureData.startX}"
                        )
                        Text(
                            text = stringResource(R.string.start_y_prefix) + "${data.gestureData.startY}"
                        )
                        Text(
                            text = stringResource(R.string.distance_x_prefix) + "${data.gestureData.distanceX}"
                        )
                        Text(
                            text = stringResource(R.string.distance_y_prefix) + "${data.gestureData.distanceY}"
                        )
                    }
                }
            }
            Icon(
                imageVector = if (expanded) {
                    Icons.Default.KeyboardArrowUp
                } else {
                    Icons.Default.KeyboardArrowDown
                },
                contentDescription = stringResource(R.string.cd_expand_log_item)
            )
        }
    }
}

@Preview
@Composable
private fun LogItemPreview() {
    ClientServerTestTaskTheme {
        LogItem(data = GestureResult(
            gestureData = Gesture(
                startX = 50,
                startY = 60,
                distanceX = .5f,
                distanceY = .6f
            ),
            startTime = 1000,
            endTime = 1100
        ))
    }
}