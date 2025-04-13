package com.example.fitness91.features.diet_plan.data.mapper

import com.example.fitness91.features.diet_plan.data.model.ExerciseResponseDto
import com.example.fitness91.features.diet_plan.domain.model.Exercise

fun ExerciseResponseDto.toExercise(): Exercise {
    return Exercise(
        id = this.id,
        mealTime = this.mealTime,
        dietDesc = this.dietDesc,
        sets = this.sets,
        unit = this.unit
    )
}