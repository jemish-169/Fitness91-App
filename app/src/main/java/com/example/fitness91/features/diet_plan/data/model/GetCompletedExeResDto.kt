package com.example.fitness91.features.diet_plan.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetCompletedExeResDto(
    @SerialName("exercise_id")
    val exerciseId: Int,

    @SerialName("sets")
    val sets: Int
)