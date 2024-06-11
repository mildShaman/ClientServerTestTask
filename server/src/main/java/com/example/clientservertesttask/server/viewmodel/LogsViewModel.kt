package com.example.clientservertesttask.server.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.clientservertesttask.server.model.DatabaseRepository
import com.example.common.GestureResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LogsViewModel @Inject constructor(
    private val repository: DatabaseRepository
): ViewModel() {
    private val _logs = MutableStateFlow<List<GestureResult>>(emptyList())
    val logs: StateFlow<List<GestureResult>> = _logs

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getAllGestureResults().collect {
                _logs.value = it
            }
        }
    }
}