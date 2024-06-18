package com.fiveoneone.cloudcompute.service.dto

data class RecommendData(
    val shuttle: BusInfo,
    val ddg: BusInfo,
    val yg: BusInfo,
    val inhaFrontGate: BusInfo) {
    data class BusInfo(
        val busStopName: String,
        val busStopNumber: String?,
        val busNumber: String,
        val remainTime: Int,
        val remainBusStop: Int,
        val congestion: Int,
        val des: String,
        val estimatedTime: Int,
        val isTransfer: Boolean
    )
}
