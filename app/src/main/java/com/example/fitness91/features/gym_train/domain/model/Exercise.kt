package com.example.fitness91.features.gym_train.domain.model

data class Exercise(
    val id: Int,
    val exerciseName: String,
    val subExercise: String,
    val sets: Int,
    val times: Int,
    val unit: String
)