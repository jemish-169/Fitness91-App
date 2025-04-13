package com.example.fitness91.core.presentation.navigation

import kotlinx.serialization.Serializable

sealed class RootRoute {

    // screens that has separate module

    @Serializable
    data object AuthNavGraph : RootRoute()

    @Serializable
    data object HomeNavGraph : RootRoute()

    @Serializable
    data object SettingNavGraph : RootRoute()

    @Serializable
    data object GymTrainNavGraph : RootRoute()

    @Serializable
    data object FaceExeNavGraph : RootRoute()

    @Serializable
    data object HistoryNavGraph : RootRoute()

    @Serializable
    data object DietPlanNavGraph : RootRoute()
}