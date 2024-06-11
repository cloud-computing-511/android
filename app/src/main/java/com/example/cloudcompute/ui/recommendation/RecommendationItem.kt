package com.example.cloudcompute.ui.recommendation

data class RecommendationItem(
    val busStopName: String,
    val subwayName: String,
    val busName: String,
    val remainTime: String,
    val estimatedTime: String
)
