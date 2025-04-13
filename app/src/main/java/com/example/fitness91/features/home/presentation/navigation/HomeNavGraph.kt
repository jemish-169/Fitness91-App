package com.example.fitness91.features.home.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.fitness91.animation.AnimateScreen
import com.example.fitness91.core.presentation.navigation.RootRoute
import com.example.fitness91.features.home.presentation.navigation.HomeRoute.HomeScreen
import com.example.fitness91.features.home.presentation.screens.HomeScreen

@Composable
fun HomeNavGraph(
    navigateToSettings: () -> Unit,
    navigateToModule: (RootRoute) -> Unit
) {
    val homeNavController = rememberNavController()

    NavHost(
        navController = homeNavController,
        startDestination = HomeScreen
    ) {

        composable<HomeScreen>(
            popEnterTransition = AnimateScreen.rightPopEnterTransition(),
            enterTransition = AnimateScreen.leftEnterTransition(),
            popExitTransition = AnimateScreen.rightPopExitTransition(),
            exitTransition = AnimateScreen.leftExitTransition()
        ) {
            HomeScreen(
                onSettingClick = navigateToSettings,
                navigateToModule = navigateToModule
            )
        }
    }
}