package com.example.cloudcompute.ui.main.home


import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.cloudcompute.repository.RetrofitClient
import com.example.cloudcompute.service.dto.ErrorResponse
import com.example.cloudcompute.service.dto.Todo
import com.google.gson.GsonBuilder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
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
        fetchSensorData()
    }


    fun getStatusText(status: Status): String {
        return when (status) {
            Status.LEISURELY -> "여유"
            Status.NORMAL -> "보통"
            Status.CROWDED -> "혼잡"
        }
    }

    private fun fetchSensorData() {
        RetrofitClient.todoApiService.getTodos().enqueue(object : Callback<List<Todo>> {
            override fun onResponse(call: Call<List<Todo>>, response: Response<List<Todo>>) {
                if (response.isSuccessful) {
                    response.body()?.let { receivedTodos ->
                        Log.d("api-result", receivedTodos[0].title)
                        // 데이터를 업데이트
                        _uiState.update { state ->
                            state.copy(
                                currentDateTime = getCurrentDateTime(),
                                status = Status.NORMAL,
                                expectedPeopleCount = 25,
                                expectedWaitTime = 22
                            )
                        }
                    }
                } else {
                    response.errorBody()?.let { errorBody ->
                        val gson = GsonBuilder().create()
                        val errorResponse = gson.fromJson(errorBody.string(), ErrorResponse::class.java)
                        Log.e("HomeViewModel", "API 요청 실패: ${errorResponse.errorMessage}")
                    }
                }
            }

            override fun onFailure(call: Call<List<Todo>>, t: Throwable) {
                Log.e("HomeViewModel", "오류 발생: ${t.message}")
            }
        })
    }



}

private fun getCurrentDateTime(): String {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm a", Locale.getDefault())
    return dateFormat.format(Date())
}

