package com.example.fitness91.features.gym_train.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ExerciseResponseDto(
    @SerialName("id")
    val id: Int,

    @SerialName("exercise_name")
    val exerciseName: String,

    @SerialName("sub_exercise")
    val subExercise: String,

    @SerialName("sets")
    val sets: Int,

    @SerialName("times")
    val times: Int,

    @SerialName("unit")
    val unit: String

)