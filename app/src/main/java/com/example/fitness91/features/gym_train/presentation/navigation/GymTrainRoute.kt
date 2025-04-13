package com.example.fitness91.features.gym_train.presentation.navigation

import kotlinx.serialization.Serializable

sealed class GymTrainRoute {

    // Settings related screens

    @Serializable
    data object GymTrainScreen : GymTrainRoute()

    @Serializable
    data object CompleteExeScreen : GymTrainRoute()
}