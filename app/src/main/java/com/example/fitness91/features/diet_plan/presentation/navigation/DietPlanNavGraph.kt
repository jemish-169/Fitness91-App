package com.example.fitness91.features.diet_plan.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.fitness91.animation.AnimateScreen
import com.example.fitness91.features.diet_plan.presentation.screen.CompleteExeScreen
import com.example.fitness91.features.diet_plan.presentation.screen.DietPlanScreen
import com.example.fitness91.features.diet_plan.presentation.viewmodel.DietPlanViewmodel

@Composable
fun DietPlanNavGraph(
    onBackOrFinish: () -> Unit
) {
    val dietPlanNavController = rememberNavController()
    val dietPlanViewmodel : DietPlanViewmodel = hiltViewModel()

    NavHost(
        navController = dietPlanNavController,
        startDestination = DietPlanRoute.DietPlanScreen
    ) {

        composable<DietPlanRoute.DietPlanScreen>(
            popEnterTransition = AnimateScreen.rightPopEnterTransition(),
            enterTransition = AnimateScreen.leftEnterTransition(),
            popExitTransition = AnimateScreen.rightPopExitTransition(),
            exitTransition = AnimateScreen.leftExitTransition()
        ) {
            DietPlanScreen(
                onBackClick = {
                    handleBackClick(dietPlanNavController, onBackOrFinish)
                },
                onExerciseClick = {
                    dietPlanNavController.navigate(DietPlanRoute.CompleteExeScreen)
            },
                dietPlanViewmodel = dietPlanViewmodel
            )
        }

        composable<DietPlanRoute.CompleteExeScreen>(
            popEnterTransition = AnimateScreen.rightPopEnterTransition(),
            enterTransition = AnimateScreen.leftEnterTransition(),
            popExitTransition = AnimateScreen.rightPopExitTransition(),
            exitTransition = AnimateScreen.leftExitTransition()
        ) {
            CompleteExeScreen(
                onBackClick = {
                    handleBackClick(dietPlanNavController, onBackOrFinish)
                },
                dietPlanViewmodel = dietPlanViewmodel
            )
        }
    }
}

private fun handleBackClick(dietPlanNavController: NavHostController, onBackOrFinish: () -> Unit) {
    if (dietPlanNavController.previousBackStackEntry == null) onBackOrFinish()
    else dietPlanNavController.navigateUp()
}