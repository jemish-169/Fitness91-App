package com.example.fitness91.features.diet_plan.domain.model

data class Exercise(
    val id: Int,
    val mealTime: String,
    val dietDesc: String,
    val sets: Int,
    val unit: String
)