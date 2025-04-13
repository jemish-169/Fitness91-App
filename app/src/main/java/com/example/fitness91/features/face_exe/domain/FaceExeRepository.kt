package com.example.fitness91.features.face_exe.domain

import com.example.fitness91.features.face_exe.domain.model.CompletedExe
import com.example.fitness91.features.face_exe.domain.model.Exercise

interface FaceExeRepository {
    suspend fun getTodayExercise(): Result<List<Exercise>>
    suspend fun getTodayCompleted(): Result<List<CompletedExe>>
    suspend fun saveExercise(value: CompletedExe): Result<Boolean>
}