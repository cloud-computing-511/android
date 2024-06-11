package com.cloud.cloudcompute.service.dto

data class ShuttleData(
    val remainTime: Int,
    val departureTime: String,
    val src: String,
    val des: String
)