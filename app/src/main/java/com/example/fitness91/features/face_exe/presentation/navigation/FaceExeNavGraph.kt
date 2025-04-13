package com.example.fitness91.features.face_exe.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.fitness91.animation.AnimateScreen
import com.example.fitness91.features.face_exe.presentation.screen.CompleteExeScreen
import com.example.fitness91.features.face_exe.presentation.screen.FaceExeScreen
import com.example.fitness91.features.face_exe.presentation.viewmodel.FaceExeViewmodel

@Composable
fun FaceExeNavGraph(
    onBackOrFinish: () -> Unit
) {
    val faceExeNavController = rememberNavController()
    val faceExeViewmodel = hiltViewModel<FaceExeViewmodel>()

    NavHost(
        navController = faceExeNavController,
        startDestination = FaceExeRoute.FaceExeScreen
    ) {

        composable<FaceExeRoute.FaceExeScreen>(
            popEnterTransition = AnimateScreen.rightPopEnterTransition(),
            enterTransition = AnimateScreen.leftEnterTransition(),
            popExitTransition = AnimateScreen.rightPopExitTransition(),
            exitTransition = AnimateScreen.leftExitTransition()
        ) {
            FaceExeScreen(
                onExerciseClick = {
                    faceExeNavController.navigate(FaceExeRoute.CompleteExeScreen)
                },
                onBackClick = {
                    handleBackClick(faceExeNavController, onBackOrFinish)
                },
                faceExeViewmodel = faceExeViewmodel
            )
        }

        composable<FaceExeRoute.CompleteExeScreen>(
            popEnterTransition = AnimateScreen.rightPopEnterTransition(),
            enterTransition = AnimateScreen.leftEnterTransition(),
            popExitTransition = AnimateScreen.rightPopExitTransition(),
            exitTransition = AnimateScreen.leftExitTransition()
        ) {
            CompleteExeScreen(
                onBackClick = {
                    handleBackClick(faceExeNavController, onBackOrFinish)
                },
                faceExeViewmodel = faceExeViewmodel
            )
        }
    }
}

private fun handleBackClick(faceExeNavController: NavHostController, onBackOrFinish: () -> Unit) {
    if (faceExeNavController.previousBackStackEntry == null) onBackOrFinish()
    else faceExeNavController.navigateUp()
}