package com.example.fitness91.features.diet_plan.presentation.model

import com.example.fitness91.features.diet_plan.domain.model.Exercise

fun Exercise.toExerciseUI(completedSet: Int): ExerciseUI {
    return ExerciseUI(
        id = this.id,
        mealTime = this.mealTime,
        dietDesc = this.dietDesc,
        sets = this.sets,
        unit = this.unit,
        completedSet = completedSet,
    )
}