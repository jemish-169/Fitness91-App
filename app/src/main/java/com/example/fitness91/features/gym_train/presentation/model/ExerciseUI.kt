package com.example.fitness91.features.gym_train.presentation.model

data class ExerciseUI(
    val id: Int = 0,
    val exerciseName: String = "",
    val subExercise: String = "",
    val sets: Int = 0,
    val times: Int = 0,
    val unit: String = "",
    var completedSet : Int = 0
)