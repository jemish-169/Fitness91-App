package com.example.fitness91.core.presentation.utils

import com.example.fitness91.core.presentation.navigation.RootRoute
import com.example.fitness91.core.presentation.navigation.RootRoute.AuthNavGraph
import com.example.fitness91.core.presentation.navigation.RootRoute.HomeNavGraph

object Utils {
    fun getStartDestination(isReady: Boolean): RootRoute {
        return if (isReady) HomeNavGraph
        else AuthNavGraph
    }
}