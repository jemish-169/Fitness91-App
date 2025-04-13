package com.example.fitness91.features.gym_train.presentation.model

import com.example.fitness91.features.gym_train.domain.model.Exercise

fun Exercise.toExerciseUI(completedSet: Int): ExerciseUI {
    return ExerciseUI(
        id = this.id,
        exerciseName = this.exerciseName,
        subExercise = this.subExercise,
        sets = this.sets,
        times = this.times,
        unit = this.unit,
        completedSet = completedSet,
    )
}