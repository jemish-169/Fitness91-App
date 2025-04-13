package com.example.fitness91.features.gym_train.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.fitness91.animation.AnimateScreen
import com.example.fitness91.features.gym_train.presentation.screen.CompleteExeScreen
import com.example.fitness91.features.gym_train.presentation.screen.GymTrainScreen
import com.example.fitness91.features.gym_train.presentation.viewmodel.GymTrainViewmodel

@Composable
fun GymTrainNavGraph(
    onBackOrFinish: () -> Unit
) {
    val gymTrainNavController = rememberNavController()
    val gymTrainViewmodel = hiltViewModel<GymTrainViewmodel>()

    NavHost(
        navController = gymTrainNavController,
        startDestination = GymTrainRoute.GymTrainScreen
    ) {

        composable<GymTrainRoute.GymTrainScreen>(
            popEnterTransition = AnimateScreen.rightPopEnterTransition(),
            enterTransition = AnimateScreen.leftEnterTransition(),
            popExitTransition = AnimateScreen.rightPopExitTransition(),
            exitTransition = AnimateScreen.leftExitTransition()
        ) {
            GymTrainScreen(
                onExerciseClick = {
                    gymTrainNavController.navigate(GymTrainRoute.CompleteExeScreen)
                },
                onBackClick = {
                    handleBackClick(gymTrainNavController, onBackOrFinish)
                },
                gymTrainViewmodel = gymTrainViewmodel
            )
        }

        composable<GymTrainRoute.CompleteExeScreen>(
            popEnterTransition = AnimateScreen.rightPopEnterTransition(),
            enterTransition = AnimateScreen.leftEnterTransition(),
            popExitTransition = AnimateScreen.rightPopExitTransition(),
            exitTransition = AnimateScreen.leftExitTransition()
        ) {
            CompleteExeScreen(
                onBackClick = {
                    handleBackClick(gymTrainNavController, onBackOrFinish)
                },
                gymTrainViewmodel = gymTrainViewmodel
            )
        }
    }
}

private fun handleBackClick(gymTrainNavController: NavHostController, onBackOrFinish: () -> Unit) {
    if (gymTrainNavController.previousBackStackEntry == null) onBackOrFinish()
    else gymTrainNavController.navigateUp()
}