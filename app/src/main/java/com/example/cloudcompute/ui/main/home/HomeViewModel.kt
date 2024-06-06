package com.example.cloudcompute.ui.main.home


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.stream.StreamSupport

import javax.inject.Inject


data class HomeUiState(
    val currentDateTime: String = "",
    val status: Status = Status.LEISURELY,
    val expectedWaitTime: Int = 0,
    val expectedPeopleCount: Int = 0
)

enum class Status {
    LEISURELY, NORMAL, CROWDED
}

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    fun setDummy() {
        _uiState.update { state ->
            state.copy(
                currentDateTime = getCurrentDateTime(),
                status = Status.LEISURELY,
                expectedPeopleCount = 15,
                expectedWaitTime = 10
            )
        }

    }

    fun getStatusText(status: Status): String {
        return when (status) {
            Status.LEISURELY -> "여유"
            Status.NORMAL -> "보통"
            Status.CROWDED -> "혼잡"
        }
    }
}

private fun getCurrentDateTime(): String {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm a", Locale.getDefault())
    return dateFormat.format(Date())
}

