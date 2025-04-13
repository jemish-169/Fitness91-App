package com.example.fitness91.features.gym_train.domain

import com.example.fitness91.features.gym_train.domain.model.Exercise
import com.example.fitness91.features.gym_train.domain.model.CompletedExe


interface GymTrainRepository {
    suspend fun getTodayExercise(): Result<List<Exercise>>
    suspend fun getTodayCompleted(): Result<List<CompletedExe>>
    suspend fun saveExercise(value: CompletedExe): Result<Boolean>
}