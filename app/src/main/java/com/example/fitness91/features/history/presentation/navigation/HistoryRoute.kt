package com.example.fitness91.features.history.presentation.navigation

import kotlinx.serialization.Serializable

sealed class HistoryRoute {

    // Settings related screens

    @Serializable
    data object HistoryScreen : HistoryRoute()
}