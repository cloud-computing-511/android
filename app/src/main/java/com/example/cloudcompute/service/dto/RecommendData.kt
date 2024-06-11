package com.example.cloudcompute.service.dto

data class RecommendData(
    val buses: List<Bus>
) {
    data class Bus(
        val busId: String,
        val busNumber: String,
        val busStopId: String,
        val busStopNumber: String,
        val busStopName: String,
        val remainTime: Int,
        val remainBusStop: Int,
        val congestion: Int
    )
}
