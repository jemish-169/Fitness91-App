package com.example.fitness91.features.auth.presentation.navigation

import kotlinx.serialization.Serializable

sealed class AuthRoute {

    // auth related screens

    @Serializable
    data object SignInScreen : AuthRoute()

    @Serializable
    data object SignUpScreen : AuthRoute()
}