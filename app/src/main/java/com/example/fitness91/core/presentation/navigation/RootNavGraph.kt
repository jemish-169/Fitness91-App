package com.example.fitness91.core.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.fitness91.animation.AnimateScreen
import com.example.fitness91.core.presentation.navigation.RootRoute.AuthNavGraph
import com.example.fitness91.core.presentation.navigation.RootRoute.HomeNavGraph
import com.example.fitness91.core.presentation.navigation.RootRoute.SettingNavGraph
import com.example.fitness91.features.auth.presentation.navigation.AuthNavGraph
import com.example.fitness91.features.diet_plan.presentation.navigation.DietPlanNavGraph
import com.example.fitness91.features.face_exe.presentation.navigation.FaceExeNavGraph
import com.example.fitness91.features.gym_train.presentation.navigation.GymTrainNavGraph
import com.example.fitness91.features.history.presentation.navigation.HistoryNavGraph
import com.example.fitness91.features.home.presentation.navigation.HomeNavGraph
import com.example.fitness91.features.settings.presentation.navigation.SettingsNavGraph

@Composable
fun RootNavGraph(
    rootNavController: NavHostController,
    startDestination: RootRoute,
    onBackOrFinish: () -> Unit,
) {

    NavHost(
        navController = rootNavController,
        startDestination = startDestination
    ) {

        composable<AuthNavGraph>(
            popEnterTransition = AnimateScreen.rightPopEnterTransition(),
            enterTransition = AnimateScreen.leftEnterTransition(),
            popExitTransition = AnimateScreen.rightPopExitTransition(),
            exitTransition = AnimateScreen.leftExitTransition()
        ) {
            AuthNavGraph(
                onBackOrFinish = onBackOrFinish,
                onOnboardingSuccess = {
                    rootNavController.navigate(HomeNavGraph) {
                        popUpTo(AuthNavGraph) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable<HomeNavGraph>(
            popEnterTransition = AnimateScreen.rightPopEnterTransition(),
            enterTransition = AnimateScreen.leftEnterTransition(),
            popExitTransition = AnimateScreen.rightPopExitTransition(),
            exitTransition = AnimateScreen.leftExitTransition()
        ) {
            HomeNavGraph(
                navigateToSettings = {
                    rootNavController.navigate(SettingNavGraph)
                },
                navigateToModule = { route ->
                    rootNavController.navigate(route)
                }
            )
        }

        composable<RootRoute.HistoryNavGraph>(
            popEnterTransition = AnimateScreen.rightPopEnterTransition(),
            enterTransition = AnimateScreen.leftEnterTransition(),
            popExitTransition = AnimateScreen.rightPopExitTransition(),
            exitTransition = AnimateScreen.leftExitTransition()
        ) {
            HistoryNavGraph(
                onBackOrFinish = onBackOrFinish
            )
        }

        composable<RootRoute.GymTrainNavGraph>(
            popEnterTransition = AnimateScreen.rightPopEnterTransition(),
            enterTransition = AnimateScreen.leftEnterTransition(),
            popExitTransition = AnimateScreen.rightPopExitTransition(),
            exitTransition = AnimateScreen.leftExitTransition()
        ) {
            GymTrainNavGraph(
                onBackOrFinish = onBackOrFinish
            )
        }

        composable<RootRoute.FaceExeNavGraph>(
            popEnterTransition = AnimateScreen.rightPopEnterTransition(),
            enterTransition = AnimateScreen.leftEnterTransition(),
            popExitTransition = AnimateScreen.rightPopExitTransition(),
            exitTransition = AnimateScreen.leftExitTransition()
        ) {
            FaceExeNavGraph(
                onBackOrFinish = onBackOrFinish
            )
        }

        composable<RootRoute.DietPlanNavGraph>(
            popEnterTransition = AnimateScreen.rightPopEnterTransition(),
            enterTransition = AnimateScreen.leftEnterTransition(),
            popExitTransition = AnimateScreen.rightPopExitTransition(),
            exitTransition = AnimateScreen.leftExitTransition()
        ) {
            DietPlanNavGraph(
                onBackOrFinish = onBackOrFinish
            )
        }

        composable<SettingNavGraph>(
            popEnterTransition = AnimateScreen.rightPopEnterTransition(),
            enterTransition = AnimateScreen.leftEnterTransition(),
            popExitTransition = AnimateScreen.rightPopExitTransition(),
            exitTransition = AnimateScreen.leftExitTransition()
        ) {
            SettingsNavGraph(
                onSignOut = {
                    rootNavController.navigate(AuthNavGraph) {
                        popUpTo(HomeNavGraph) {
                            inclusive = true
                        }
                    }
                },
                onBackOrFinish = onBackOrFinish
            )
        }
    }
}