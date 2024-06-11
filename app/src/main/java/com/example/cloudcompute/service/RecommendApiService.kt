package com.example.cloudcompute.service

import com.example.cloudcompute.service.dto.RecommendData
import retrofit2.Call
import retrofit2.http.GET

interface RecommendApiService {

    /**
     * [GET] 추천 버스 조회하기
     */
    @GET("/bus/recommend")
    fun getRecommend(): Call<RecommendData>
}