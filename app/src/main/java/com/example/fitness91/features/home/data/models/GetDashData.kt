package com.example.fitness91.features.home.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetDashData(
    @SerialName("p_day")
    val pDay: Int,

    @SerialName("p_created_by")
    val pCreatedBy: String
)
