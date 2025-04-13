package com.example.fitness91.features.settings.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.fitness91.animation.AnimateScreen
import com.example.fitness91.features.settings.presentation.navigation.SettingsRoute.SettingsScreen
import com.example.fitness91.features.settings.presentation.screen.SettingScreen
import com.example.fitness91.features.settings.presentation.viewmodel.SettingsViewModel

@Composable
fun SettingsNavGraph(
    onSignOut: () -> Unit,
    onBackOrFinish: () -> Unit
) {
    val settingsNavController = rememberNavController()
    val settingsViewModel: SettingsViewModel = hiltViewModel<SettingsViewModel>()

    NavHost(
        navController = settingsNavController,
        startDestination = SettingsScreen
    ) {

        composable<SettingsScreen>(
            popEnterTransition = AnimateScreen.rightPopEnterTransition(),
            enterTransition = AnimateScreen.leftEnterTransition(),
            popExitTransition = AnimateScreen.rightPopExitTransition(),
            exitTransition = AnimateScreen.leftExitTransition()
        ) {
            SettingScreen(
                onSignOut = onSignOut,
                onBackClick = {
                    handleBackClick(settingsNavController, onBackOrFinish)
                },
                settingsViewModel = settingsViewModel
            )
        }
    }
}

private fun handleBackClick(settingsNavController: NavHostController, onBackOrFinish: () -> Unit) {
    if (settingsNavController.previousBackStackEntry == null) onBackOrFinish()
    else settingsNavController.navigateUp()
}