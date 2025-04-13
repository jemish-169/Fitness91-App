package com.example.fitness91.features.diet_plan.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ExerciseResponseDto(
    @SerialName("id")
    val id: Int,

    @SerialName("meal_time")
    val mealTime: String,

    @SerialName("diet_desc")
    val dietDesc: String,

    @SerialName("sets")
    val sets: Int,

    @SerialName("unit")
    val unit: String

)