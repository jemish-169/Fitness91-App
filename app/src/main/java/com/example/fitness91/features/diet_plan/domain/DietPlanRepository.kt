package com.example.fitness91.features.diet_plan.domain

import com.example.fitness91.features.diet_plan.domain.model.CompletedExe
import com.example.fitness91.features.diet_plan.domain.model.Exercise

interface DietPlanRepository {
    suspend fun getTodayExercise(): Result<List<Exercise>>
    suspend fun getTodayCompleted(): Result<List<CompletedExe>>
    suspend fun saveExercise(value: CompletedExe): Result<Boolean>
}