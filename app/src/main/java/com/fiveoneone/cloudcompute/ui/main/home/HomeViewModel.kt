package com.fiveoneone.cloudcompute.ui.main.home


import android.util.Log
import androidx.lifecycle.ViewModel
import com.fiveoneone.cloudcompute.repository.RetrofitClient
import com.fiveoneone.cloudcompute.service.dto.CongestionData
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
    val status: Status? = Status.SENSOR_INACTIVE,
    val expectedWaitTime: String = "-",
    val expectedPeopleCount: String = "-",
    val isLoading: Boolean = true
)

enum class Status {
    LEISURELY, NORMAL, CROWDED, SENSOR_ERROR, SENSOR_INACTIVE
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
            Status.SENSOR_ERROR -> "센서 이상"
            Status.SENSOR_INACTIVE -> "센서 미작동 시간"
        }
    }

    fun getStatusFromText(text: String): Status {
        return when (text) {
            "SPARE" -> Status.LEISURELY
            "NORMAL" -> Status.NORMAL
            "CONGESTION" -> Status.CROWDED
            "SENSOR_ERROR" -> Status.SENSOR_ERROR
            "SENSOR_INACTIVE" -> Status.SENSOR_INACTIVE
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
                                    //status = getStatusFromText(it.congestion),
                                    status = Status.SENSOR_INACTIVE,
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