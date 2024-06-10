package com.example.cloudcompute.service.dto

data class SensorData(
    val currentLocation : String,
    val currentDateTime : String,
    val congestion: String,
    val expectedWaitingTime: Int,
    val expectedWaitingPeople: Int
    )