package com.example.fitness91.features.history.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DailyDataResponseDto(

    @SerialName("date")
    val date: String,

    @SerialName("total_sets")
    val totalSets: Int
)
