package com.example.fitness91.features.history.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetDailySalesDto(
    @SerialName("start_date")
    val startDate: String,

    @SerialName("end_date")
    val endDate: String,

    @SerialName("p_created_by")
    val id: String
)