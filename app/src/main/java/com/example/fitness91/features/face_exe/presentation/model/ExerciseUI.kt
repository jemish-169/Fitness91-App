package com.example.fitness91.features.face_exe.presentation.model

data class ExerciseUI(
    val id: Int = 0,
    val exerciseName: String = "",
    val sets: Int = 0,
    val unit: String = "",
    var completedSet : Int = 0
)