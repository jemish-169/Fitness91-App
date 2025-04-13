package com.example.fitness91.features.auth.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.fitness91.animation.AnimateScreen
import com.example.fitness91.features.auth.presentation.navigation.AuthRoute.SignInScreen
import com.example.fitness91.features.auth.presentation.navigation.AuthRoute.SignUpScreen
import com.example.fitness91.features.auth.presentation.screen.SignInScreen
import com.example.fitness91.features.auth.presentation.screen.SignUpScreen
import com.example.fitness91.features.auth.presentation.viewmodel.AuthViewModel

@Composable
fun AuthNavGraph(
    onBackOrFinish: () -> Unit,
    onOnboardingSuccess: () -> Unit
) {
    val authNavController = rememberNavController()
    val authViewModel: AuthViewModel = hiltViewModel()

    NavHost(
        navController = authNavController,
        startDestination = SignInScreen
    ) {

        composable<SignInScreen>(
            popEnterTransition = AnimateScreen.rightPopEnterTransition(),
            enterTransition = AnimateScreen.leftEnterTransition(),
            popExitTransition = AnimateScreen.rightPopExitTransition(),
            exitTransition = AnimateScreen.leftExitTransition()
        ) {
            SignInScreen(
                onSignInSuccess = onOnboardingSuccess,
                moveToSignUp = {
                    authNavController.navigate(SignUpScreen)
                },
                authViewModel = authViewModel
            )
        }

        composable<SignUpScreen>(
            popEnterTransition = AnimateScreen.rightPopEnterTransition(),
            enterTransition = AnimateScreen.leftEnterTransition(),
            popExitTransition = AnimateScreen.rightPopExitTransition(),
            exitTransition = AnimateScreen.leftExitTransition()
        ) {
            SignUpScreen(
                onBackClick = {
                    handleBackClick(authNavController, onBackOrFinish)
                },
                moveToSignIn = { authNavController.navigateUp() },
                onSignUpSuccess = onOnboardingSuccess,
                authViewModel = authViewModel
            )
        }
    }
}

private fun handleBackClick(authNavController: NavHostController, onBackOrFinish: () -> Unit) {
    if (authNavController.previousBackStackEntry == null) onBackOrFinish()
    else authNavController.navigateUp()
}