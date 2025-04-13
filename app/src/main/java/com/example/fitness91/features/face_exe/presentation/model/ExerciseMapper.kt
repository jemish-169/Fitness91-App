package com.example.fitness91.features.face_exe.presentation.model

import com.example.fitness91.features.face_exe.domain.model.Exercise

fun Exercise.toExerciseUI(completedSet: Int): ExerciseUI {
    return ExerciseUI(
        id = this.id,
        exerciseName = this.exerciseName,
        sets = this.sets,
        unit = this.unit,
        completedSet = completedSet,
    )
}