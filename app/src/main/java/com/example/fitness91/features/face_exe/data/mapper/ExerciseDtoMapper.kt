package com.example.fitness91.features.face_exe.data.mapper

import com.example.fitness91.features.face_exe.data.model.ExerciseResponseDto
import com.example.fitness91.features.face_exe.domain.model.Exercise

fun ExerciseResponseDto.toExercise(): Exercise {
    return Exercise(
        id = this.id,
        exerciseName = this.exerciseName,
        sets = this.sets,
        unit = this.unit
    )
}