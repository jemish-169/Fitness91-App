package com.example.fitness91.features.face_exe.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ExerciseResponseDto(
    @SerialName("id")
    val id: Int,

    @SerialName("exercise_name")
    val exerciseName: String,

    @SerialName("sets")
    val sets: Int,

    @SerialName("unit")
    val unit: String

)