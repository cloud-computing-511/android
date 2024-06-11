package com.cloud.cloudcompute.service.dto

data class CongestionData(
    val currentLocation : String,
    val currentDateTime : String,
    val congestion: String,
    val expectedWaitingTime: Int,
    val expectedWaitingPeople: Int
    )