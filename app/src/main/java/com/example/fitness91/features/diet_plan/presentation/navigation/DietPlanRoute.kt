package com.example.fitness91.features.diet_plan.presentation.navigation

import kotlinx.serialization.Serializable

sealed class DietPlanRoute {

    // Settings related screens

    @Serializable
    data object DietPlanScreen : DietPlanRoute()

    @Serializable
    data object CompleteExeScreen : DietPlanRoute()
}