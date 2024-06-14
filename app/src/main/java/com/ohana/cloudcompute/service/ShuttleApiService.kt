package com.ohana.cloudcompute.service

import com.ohana.cloudcompute.service.dto.ShuttleData
import retrofit2.Call
import retrofit2.http.GET

interface ShuttleApiService {

    /**
     * [GET] 셔틀 버스 조회하기
     */
    @GET("/bus/shuttle")
    fun getShuttle(): Call<ShuttleData>
}