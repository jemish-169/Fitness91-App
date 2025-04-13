package com.example.fitness91.features.diet_plan.presentation.model

data class ExerciseUI(
    val id: Int = 0,
    val mealTime: String = "",
    val dietDesc: String = "",
    val sets: Int = 0,
    val unit: String = "",
    var completedSet : Int = 0
)