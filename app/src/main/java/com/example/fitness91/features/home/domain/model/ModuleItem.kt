package com.example.fitness91.features.home.domain.model

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.fitness91.core.presentation.navigation.RootRoute

data class ModuleItem(
    val title: String,
    val backgroundColor: Color,
    val contentColor: Color,
    val icon: ImageVector,
    val route: RootRoute
)