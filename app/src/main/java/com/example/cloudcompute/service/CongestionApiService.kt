package com.example.cloudcompute.service

import com.example.cloudcompute.service.dto.CongestionData
import retrofit2.Call
import retrofit2.http.GET

interface CongestionApiService {

    /**
     * [GET] 혼잡도 조회하기
     */
    @GET("/congestion")
    fun getCongestion(): Call<CongestionData>
}