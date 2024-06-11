package com.cloud.cloudcompute.ui.recommendation

data class RecommendItem(
    val busStopName: String,
    val subwayName: String,
    val busName: String,
    var remainTime: Long,
    val estimatedTime: String,
)
