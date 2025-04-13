package com.example.fitness91.features.home.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DashDataDto(
    @SerialName("gym_completed")
    val gymCompleted: Int,

    @SerialName("gym_target")
    val gymTarget: Int,

    @SerialName("diet_completed")
    val dietCompleted: Int,

    @SerialName("face_target")
    val dietTarget: Int,

    @SerialName("face_completed")
    val faceCompleted: Int,

    @SerialName("diet_target")
    val faceTarget: Int
)
