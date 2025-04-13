package com.example.fitness91.features.history.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.outlined.BarChart
import androidx.compose.material.icons.outlined.Description
import androidx.compose.material.icons.outlined.Inventory2
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.fitness91.R
import com.example.fitness91.common.domain.state.AppState
import com.example.fitness91.common.presentation.HeaderBackIcon
import com.example.fitness91.features.history.presentation.components.ChartUI
import com.example.fitness91.features.history.presentation.components.MonthPickerDialog
import com.example.fitness91.features.history.presentation.models.DailyDataProducer
import com.example.fitness91.features.history.presentation.viewmodel.HistoryViewmodel
import com.example.fitness91.theme.green
import com.example.fitness91.theme.lightGreen
import com.example.fitness91.theme.lightOrange
import com.example.fitness91.theme.lightPink
import com.example.fitness91.theme.orange
import com.example.fitness91.theme.pink
import java.time.LocalDate

@Composable
fun HistoryScreen(
    onBackClick: () -> Unit,
    historyViewmodel: HistoryViewmodel = hiltViewModel()
) {
    var selectedDate by historyViewmodel.selectedDate
    var showDatePicker by remember { mutableStateOf(false) }

    val years by historyViewmodel.years
    val months by historyViewmodel.months

    val chartDataState by historyViewmodel.chartDataState.collectAsState()

    if (showDatePicker) {
        MonthPickerDialog(
            selectedDate = selectedDate,
            onDateSelected = { year, month ->
                selectedDate = LocalDate.of(year, month, 1)
                historyViewmodel.getMonthData(selectedDate)
                showDatePicker = false
            },
            onDismiss = { showDatePicker = false },
            years = years,
            months = months
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(12.dp)
    ) {
        HeaderBackIcon(
            headerText = stringResource(R.string.history),
            onBackClick = onBackClick,
            endIcon = {
                Button(
                    onClick = { showDatePicker = true },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary.copy(0.1f)
                    ),
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.CalendarToday,
                        contentDescription = stringResource(R.string.calendar),
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = historyViewmodel.formatMonthYear(selectedDate),
                        color = MaterialTheme.colorScheme.primary,
                        fontSize = 14.sp
                    )
                }
            })

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            when (chartDataState) {
                is AppState.Success -> {
                    val chartData = (chartDataState as AppState.Success<DailyDataProducer>).message

                    item(key = R.string.gym_training) {
                        ChartUI(
                            icon = Icons.Outlined.BarChart,
                            title = stringResource(R.string.daily_gym_exercises),
                            iconBackgroundColor = lightPink,
                            iconColor = pink,
                            chartModel = chartData.gymExeModel,
                            chartColor = pink
                        )
                    }

                    item(key = R.string.diet_plan) {
                        ChartUI(
                            icon = Icons.Outlined.Description,
                            title = stringResource(R.string.daily_diet_plan),
                            iconBackgroundColor = lightGreen,
                            iconColor = green,
                            chartModel = chartData.dietPlanModel,
                            chartColor = green
                        )
                    }

                    item(key = R.string.face_exercise) {
                        ChartUI(
                            icon = Icons.Outlined.Inventory2,
                            title = stringResource(R.string.daily_face_exercises),
                            iconBackgroundColor = lightOrange,
                            iconColor = orange,
                            chartModel = chartData.faceExeModel,
                            chartColor = orange
                        )
                    }
                }

                is AppState.Loading -> {
                    item(key = R.string.loading) {
                        CircularProgressIndicator()
                    }
                }

                is AppState.Error -> {
                    item(key = R.string.error) {
                        Text(
                            text = (chartDataState as AppState.Error<String>).error,
                            textAlign = TextAlign.Center,
                            fontSize = 18.sp,
                            lineHeight = 22.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onSurface,
                            letterSpacing = 0.8.sp
                        )
                    }
                }

                else -> {
                    item(key = R.string.idle) {}
                }
            }
        }
    }
}
