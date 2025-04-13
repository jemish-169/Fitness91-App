package com.example.fitness91.features.face_exe.presentation.navigation

import kotlinx.serialization.Serializable

sealed class FaceExeRoute {

    // Settings related screens

    @Serializable
    data object FaceExeScreen : FaceExeRoute()

    @Serializable
    data object CompleteExeScreen : FaceExeRoute()
}