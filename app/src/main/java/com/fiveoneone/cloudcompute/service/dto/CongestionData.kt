package com.fiveoneone.cloudcompute.service.dto

data class CongestionData(
    val currentLocation : String,
    val currentDateTime : String,
    val congestion: String,
    val expectedWaitingTime: String,
    val expectedWaitingPeople: String
    )