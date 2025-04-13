package com.example.fitness91.features.history.presentation.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitness91.R
import com.example.fitness91.common.domain.state.AppState
import com.example.fitness91.di.ResourceManager
import com.example.fitness91.features.history.domain.HistoryRepository
import com.example.fitness91.features.history.presentation.models.DailyDataProducer
import com.example.fitness91.features.history.presentation.models.toFloatEntries
import com.example.fitness91.features.history.presentation.utils.toSupDate
import com.patrykandpatrick.vico.core.entry.ChartEntryModelProducer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class HistoryViewmodel @Inject constructor(
    private val historyRepository: HistoryRepository,
    private val resourceManager: ResourceManager
) : ViewModel() {

    val years = mutableStateOf(emptyList<Int>())
    val months = mutableStateOf(emptyList<String>())
    val selectedDate = mutableStateOf(LocalDate.now())

    private val _cartDataState =
        MutableStateFlow<AppState<DailyDataProducer, String>>(AppState.Idle)
    val chartDataState: StateFlow<AppState<DailyDataProducer, String>> =
        _cartDataState
            .onStart { getMonthData(selectedDate.value) }
            .stateIn(viewModelScope, SharingStarted.Eagerly, AppState.Loading)

    init {
        viewModelScope.launch {
            years.value = historyRepository.getYearList()
            months.value = historyRepository.getMonthList()
        }
    }

    fun getMonthData(selectedDate: LocalDate) {
        viewModelScope.launch {
            _cartDataState.value = AppState.Loading
            try {
                val result = historyRepository.getMonthData(selectedDate.toSupDate())
                _cartDataState.value = result.fold(
                    onSuccess = { data ->
                        withContext(Dispatchers.Default) {
                            if (data.isEmpty()) AppState.Error(resourceManager.getString(R.string.no_data_found))
                            else {
                                val gymExeModel =
                                    ChartEntryModelProducer(data.toFloatEntries { gymExe.toFloat() })
                                val faceExeModel =
                                    ChartEntryModelProducer(data.toFloatEntries { faceExe.toFloat() })
                                val dietPlanModel =
                                    ChartEntryModelProducer(data.toFloatEntries { dietPlan.toFloat() })
                                AppState.Success(
                                    DailyDataProducer(gymExeModel, faceExeModel, dietPlanModel)
                                )
                            }
                        }
                    },
                    onFailure = {
                        AppState.Error(
                            it.message
                                ?: resourceManager.getString(R.string.failed_to_get_daily_data)
                        )
                    }
                )
            } catch (e: Exception) {
                _cartDataState.value = AppState.Error(
                    e.message ?: resourceManager.getString(R.string.unknown_error_occurred)
                )
            }
        }
    }

    fun formatMonthYear(date: LocalDate): String {
        return date.format(DateTimeFormatter.ofPattern("MMM yy"))
    }
    
}