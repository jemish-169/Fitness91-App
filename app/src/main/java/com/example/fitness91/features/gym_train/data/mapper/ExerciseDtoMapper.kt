package com.example.fitness91.features.gym_train.data.mapper

import com.example.fitness91.features.gym_train.data.model.ExerciseResponseDto
import com.example.fitness91.features.gym_train.domain.model.Exercise

fun ExerciseResponseDto.toExercise(): Exercise {
    return Exercise(
        id = this.id,
        exerciseName = this.exerciseName,
        subExercise = this.subExercise,
        sets = this.sets,
        times = this.times,
        unit = this.unit
    )
}