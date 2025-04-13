package com.example.fitness91.features.home.presentation.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.fitness91.R
import com.example.fitness91.common.domain.state.AppState
import com.example.fitness91.common.presentation.shimmerEffect
import com.example.fitness91.core.presentation.navigation.RootRoute
import com.example.fitness91.features.home.domain.model.ModuleItem
import com.example.fitness91.features.home.presentation.components.HomeDashDataItem
import com.example.fitness91.features.home.presentation.components.HomeHeader
import com.example.fitness91.features.home.presentation.components.ModuleItemUI
import com.example.fitness91.features.home.presentation.models.DashDataUI
import com.example.fitness91.features.home.presentation.viewmodel.HomeViewModel
import com.example.fitness91.theme.completed
import com.example.fitness91.theme.green
import com.example.fitness91.theme.lightGreen
import com.example.fitness91.theme.lightOrange
import com.example.fitness91.theme.lightPink
import com.example.fitness91.theme.lightPurple
import com.example.fitness91.theme.notCompleted
import com.example.fitness91.theme.notStarted
import com.example.fitness91.theme.orange
import com.example.fitness91.theme.pink
import com.example.fitness91.theme.purple
import com.example.fitness91.theme.started

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onSettingClick: () -> Unit,
    navigateToModule: (RootRoute) -> Unit,
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val fitnessModules = getFitnessModules()

    val userState by homeViewModel.userState.collectAsStateWithLifecycle()
    val dashBoardData by homeViewModel.dashBoardData.collectAsStateWithLifecycle()

    var dashData by remember { mutableStateOf(DashDataUI()) }
    var overallPercentage by remember { mutableIntStateOf(0) }
    LaunchedEffect(dashBoardData) {
        if (dashBoardData is AppState.Success) {
            (dashBoardData as AppState.Success).message.let {
                dashData = it
                overallPercentage =
                    ((it.gymPercentage + it.dietPercentage + it.facePercentage) / 3)

            }
        }
    }
    val isRefresh by homeViewModel.isRefresh

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(12.dp)
    ) {
        HomeHeader(userName = userState.firstName, onSettingClick = onSettingClick)

        PullToRefreshBox(
            isRefreshing = isRefresh,
            onRefresh = { homeViewModel.getDashBoardData() },
        ) {
            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 24.dp, bottom = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(R.string.today_s_progress),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    AnimatedVisibility(
                        dashBoardData is AppState.Loading,
                        enter = expandHorizontally(),
                        exit = shrinkHorizontally()
                    ) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(2.dp))
                                .width(30.dp)
                                .height(16.dp)
                                .shimmerEffect()
                        )
                    }
                    AnimatedVisibility(
                        dashBoardData !is AppState.Loading,
                        enter = expandHorizontally(),
                        exit = shrinkHorizontally()
                    ) {
                        Text(
                            text = stringResource(R.string.overall_percentage, overallPercentage),
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = when {
                                overallPercentage < 25 -> notStarted
                                overallPercentage < 50 -> started
                                overallPercentage < 80 -> notCompleted
                                else -> completed
                            }
                        )
                    }
                }

                AnimatedVisibility(
                    dashBoardData is AppState.Loading,
                    enter = expandVertically(),
                    exit = shrinkVertically()
                ) {
                    Box(
                        modifier = Modifier
                            .padding(bottom = 16.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .fillMaxWidth()
                            .height(68.dp)
                            .shimmerEffect()
                    )
                }
                AnimatedVisibility(
                    dashBoardData !is AppState.Loading,
                    enter = expandVertically(),
                    exit = shrinkVertically()
                ) {
                    Row(
                        modifier = Modifier
                            .padding(bottom = 16.dp)
                            .horizontalScroll(rememberScrollState())
                            .height(IntrinsicSize.Min)
                    ) {
                        HomeDashDataItem(
                            completedText = dashData.gymCompleted,
                            target = dashData.gymTarget,
                            percentage = dashData.gymPercentage,
                            title = R.string.gym
                        )

                        VerticalDivider(
                            thickness = 2.dp,
                            modifier = Modifier.padding(horizontal = 16.dp)
                        )

                        HomeDashDataItem(
                            completedText = dashData.dietCompleted,
                            target = dashData.dietTarget,
                            percentage = dashData.dietPercentage,
                            title = R.string.diet
                        )

                        VerticalDivider(
                            thickness = 2.dp,
                            modifier = Modifier.padding(horizontal = 16.dp)
                        )

                        HomeDashDataItem(
                            completedText = dashData.faceCompleted,
                            target = dashData.faceTarget,
                            percentage = dashData.facePercentage,
                            title = R.string.facial
                        )
                    }
                }

                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(fitnessModules, key = { it.route.toString() }) { module ->
                        ModuleItemUI(module) {
                            navigateToModule(module.route)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun getFitnessModules() = listOf(
    ModuleItem(
        title = stringResource(R.string.gym_training),
        backgroundColor = lightPink,
        contentColor = pink,
        icon = Icons.Filled.FitnessCenter,
        route = RootRoute.GymTrainNavGraph
    ),
    ModuleItem(
        title = stringResource(R.string.diet_and_food),
        backgroundColor = lightGreen,
        contentColor = green,
        icon = Icons.Filled.Restaurant,
        route = RootRoute.DietPlanNavGraph
    ),
    ModuleItem(
        title = stringResource(R.string.face_exercise),
        backgroundColor = lightOrange,
        contentColor = orange,
        icon = Icons.Filled.Face,
        route = RootRoute.FaceExeNavGraph
    ),
    ModuleItem(
        title = stringResource(R.string.history),
        backgroundColor = lightPurple,
        contentColor = purple,
        icon = Icons.AutoMirrored.Filled.TrendingUp,
        route = RootRoute.HistoryNavGraph
    )
)