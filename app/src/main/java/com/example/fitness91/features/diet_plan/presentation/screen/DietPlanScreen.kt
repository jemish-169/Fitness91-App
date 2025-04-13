package com.example.fitness91.features.diet_plan.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.fitness91.R
import com.example.fitness91.common.domain.state.AppState
import com.example.fitness91.common.presentation.HeaderBackIcon
import com.example.fitness91.features.diet_plan.presentation.components.ExerciseItemUI
import com.example.fitness91.features.diet_plan.presentation.viewmodel.DietPlanViewmodel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DietPlanScreen(
    onBackClick: () -> Unit,
    onExerciseClick: () -> Unit,
    dietPlanViewmodel: DietPlanViewmodel
) {
    val exerciseState by dietPlanViewmodel.exerciseListState.collectAsStateWithLifecycle()
    val isRefresh by dietPlanViewmodel.isRefresh

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(12.dp)
    ) {

        HeaderBackIcon(
            headerText = stringResource(R.string.today_diet),
            onBackClick = onBackClick
        )

        PullToRefreshBox(
            isRefreshing = isRefresh,
            onRefresh = { dietPlanViewmodel.getTodayExercise() },
        ) {
            LazyColumn(
                modifier = Modifier
                    .padding(top = 8.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                when (exerciseState) {
                    is AppState.Loading -> {
                        item {
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier.fillParentMaxSize()
                            ) { CircularProgressIndicator() }
                        }
                    }

                    is AppState.Error -> {
                        item {
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier.fillParentMaxSize()
                            ) {
                                Text(
                                    text = (exerciseState as AppState.Error<String>).error,
                                    textAlign = TextAlign.Center,
                                    fontSize = 18.sp,
                                    lineHeight = 22.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = MaterialTheme.colorScheme.onSurface,
                                    letterSpacing = 0.8.sp
                                )
                            }
                        }
                    }

                    is AppState.Success -> {
                        val exercises = (exerciseState as AppState.Success).message
                        items(items = exercises, key = { it.id }) { exercise ->
                            ExerciseItemUI(
                                exercise = exercise,
                                onExerciseClick = {
                                    dietPlanViewmodel.setToBeCompletedExe(exercise)
                                    onExerciseClick()
                                }
                            )
                        }
                    }

                    else -> {}
                }
            }
        }
    }
}
