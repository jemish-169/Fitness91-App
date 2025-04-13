package com.example.fitness91.features.settings.presentation.navigation

import kotlinx.serialization.Serializable

sealed class SettingsRoute {

    // Settings related screens

    @Serializable
    data object SettingsScreen : SettingsRoute()
}