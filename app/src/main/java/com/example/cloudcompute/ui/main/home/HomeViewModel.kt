package com.example.cloudcompute.ui.main.home


import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.cloudcompute.repository.RetrofitClient
import com.example.cloudcompute.service.dto.CongestionData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject


data class HomeUiState(
    val currentDateTime: String = "",
    val status: Status? = Status.CROWDED,
    val expectedWaitTime: Int = 0,
    val expectedPeopleCount: Int = 0,
    val isLoading: Boolean = true
)

enum class Status {
    LEISURELY, NORMAL, CROWDED
}

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    fun getStatusText(status: Status): String {
        return when (status) {
            Status.LEISURELY -> "여유"
            Status.NORMAL -> "보통"
            Status.CROWDED -> "혼잡"
        }
    }

    fun getStatusFromText(text: String): Status {
        return when (text) {
            "여유" -> Status.LEISURELY
            "일반" -> Status.NORMAL
            "혼잡" -> Status.CROWDED
            else -> throw IllegalArgumentException("Unknown status text: $text")
        }
    }

    fun fetchCongestion() {

        _uiState.update { it.copy(isLoading = true) }

        RetrofitClient.congestionService.getCongestion()
            .enqueue(object : Callback<CongestionData> {
                override fun onResponse(call: Call<CongestionData>, response: Response<CongestionData>) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            Log.d("debugging", "$it")
                            _uiState.update {state ->
                                state.copy(
                                    currentDateTime = it.currentDateTime,
                                    status = getStatusFromText(it.congestion),
                                    expectedWaitTime = it.expectedWaitingTime,
                                    expectedPeopleCount = it.expectedWaitingPeople,
                                    isLoading = false
                                )

                            }
                        }
                    }
                }

                override fun onFailure(call: Call<CongestionData>, t: Throwable) {
                    Log.e("debugging", "API 요청 중 오류 발생: ${t.message}")
                }
            })
    }



}