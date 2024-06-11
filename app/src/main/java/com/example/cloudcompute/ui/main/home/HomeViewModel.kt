package com.example.cloudcompute.ui.main.home


import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.cloudcompute.repository.RetrofitClient
import com.example.cloudcompute.service.dto.RecommendData
import com.example.cloudcompute.service.dto.CongestionData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
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

    init {
        fetchCongestion()
        fetchRecommend()
    }


    fun getStatusText(status: Status): String {
        return when (status) {
            Status.LEISURELY -> "여유"
            Status.NORMAL -> "보통"
            Status.CROWDED -> "혼잡"
        }
    }

    fun fetchCongestion() {
        RetrofitClient.congestionService.getCongestion()
            .enqueue(object : Callback<CongestionData> {
                override fun onResponse(call: Call<CongestionData>, response: Response<CongestionData>) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            Log.d("debugging", "$it")
                        }
                    }
                }

                override fun onFailure(call: Call<CongestionData>, t: Throwable) {
                    Log.e("debugging", "API 요청 중 오류 발생: ${t.message}")
                }
            })
    }

    fun fetchRecommend() {
        RetrofitClient.recommendService.getRecommend()
            .enqueue(object : Callback<RecommendData> {
                override fun onResponse(call: Call<RecommendData>, response: Response<RecommendData>) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            Log.d("debugging", "$it")
                        }
                    }
                }

                override fun onFailure(call: Call<RecommendData>, t: Throwable) {
                    Log.e("debugging", "API 요청 중 오류 발생: ${t.message}")
                }
            })
    }


}

private fun getCurrentDateTime(): String {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm a", Locale.getDefault())
    return dateFormat.format(Date())
}

