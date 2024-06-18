package com.fiveoneone.cloudcompute.ui.recommendation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fiveoneone.cloudcompute.repository.RetrofitClient
import com.fiveoneone.cloudcompute.service.dto.RecommendData
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class RecommendViewModel @Inject constructor() : ViewModel() {

    private val _busData = MutableLiveData<List<RecommendItem>>()
    val busData: LiveData<List<RecommendItem>> get() = _busData

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    init {
        fetchRecommend()
    }

    fun fetchRecommend() {
        _isLoading.value = true
        RetrofitClient.recommendService.getRecommend()
            .enqueue(object : Callback<RecommendData> {
                override fun onResponse(call: Call<RecommendData>, response: Response<RecommendData>) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            val dataList = listOf(
                                it.shuttle.toRecommendItem(),
                                it.ddg.toRecommendItem(),
                                it.yg.toRecommendItem(),
                                it.inhaFrontGate.toRecommendItem()
                            )
                            _busData.value = dataList
                            _isLoading.value = false
                        }
                    } else {
                        Log.e("RecommendViewModel", "Response was not successful")
                        _isLoading.value = false
                    }
                }

                override fun onFailure(call: Call<RecommendData>, t: Throwable) {
                    Log.e("RecommendViewModel", "API 요청 중 오류 발생: ${t.message}")
                    _isLoading.value = false
                }
            })
    }

    private fun RecommendData.BusInfo.toRecommendItem(): RecommendItem {
        return RecommendItem(
            busStopName = busStopName + if (!busStopNumber.isNullOrEmpty()) "($busStopNumber)" else "",
            subwayName = des,
            busName = busNumber,
            remainTime = remainTime * 1000L,
            estimatedTime = "예상 소요 ${estimatedTime}분"
        )
    }
}