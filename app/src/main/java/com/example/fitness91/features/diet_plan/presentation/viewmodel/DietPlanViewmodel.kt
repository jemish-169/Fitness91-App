package com.example.fitness91.features.diet_plan.presentation.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitness91.R
import com.example.fitness91.common.domain.state.AppState
import com.example.fitness91.di.ResourceManager
import com.example.fitness91.features.diet_plan.domain.DietPlanRepository
import com.example.fitness91.features.diet_plan.domain.model.CompletedExe
import com.example.fitness91.features.diet_plan.presentation.model.ExerciseUI
import com.example.fitness91.features.diet_plan.presentation.model.toExerciseUI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DietPlanViewmodel @Inject constructor(
    private val dietPlanRepository: DietPlanRepository,
    private val resourceManager: ResourceManager
) : ViewModel() {
    private val _exerciseListState =
        MutableStateFlow<AppState<List<ExerciseUI>, String>>(AppState.Idle)
    val exerciseListState: StateFlow<AppState<List<ExerciseUI>, String>> =
        _exerciseListState
            .onStart { getTodayExercise(false) }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(1000), AppState.Idle)

    private val _updateExerciseState = MutableStateFlow<AppState<Boolean, String>>(AppState.Idle)
    val updateExerciseState: StateFlow<AppState<Boolean, String>> = _updateExerciseState

    private val _toBeCompleteExe = MutableStateFlow(ExerciseUI())
    val toBeCompleteExe: StateFlow<ExerciseUI> = _toBeCompleteExe

    val isRefresh = mutableStateOf(false)

    fun getTodayExercise(isFromRefresh: Boolean = true) {
        viewModelScope.launch {
            _exerciseListState.value = AppState.Loading

            try {
                if (isFromRefresh) isRefresh.value = true
                coroutineScope {
                    val todayExerciseDeferred = async { dietPlanRepository.getTodayExercise() }
                    val completedDeferred =
                        async { dietPlanRepository.getTodayCompleted() }

                    val todayExerciseResult = todayExerciseDeferred.await()
                    val completedExeResult = completedDeferred.await()

                    if (todayExerciseResult.isSuccess && completedExeResult.isSuccess) {

                        val todayExercises = todayExerciseResult.getOrThrow()
                        val completedExe = completedExeResult.getOrThrow()

                        if (todayExercises.isNotEmpty()) {

                            val completedMap =
                                completedExe.associateBy({ it.exerciseId }, { it.sets })
                            _exerciseListState.value = AppState.Success(
                                todayExercises.map { today ->
                                    today.toExerciseUI(
                                        completedSet = completedMap[today.id] ?: 0
                                    )
                                }
                            )
                        } else {
                            _exerciseListState.value =
                                AppState.Error(resourceManager.getString(R.string.no_exercise_found))
                        }
                    } else {
                        val errorMessage = todayExerciseResult.exceptionOrNull()?.message
                            ?: completedExeResult.exceptionOrNull()?.message
                            ?: resourceManager.getString(R.string.failed_to_fetch_exercise)

                        _exerciseListState.value = AppState.Error(errorMessage)
                    }
                }
                if (isFromRefresh) isRefresh.value = false
            } catch (e: Exception) {
                if (isFromRefresh) isRefresh.value = false
                _exerciseListState.value = AppState.Error(
                    e.message ?: resourceManager.getString(R.string.unknown_error_occurred)
                )
            }
        }
    }

    fun onCompleteSet(exerciseId: Int) {
        viewModelScope.launch {
            _updateExerciseState.value = AppState.Loading

            try {
                val result = dietPlanRepository.saveExercise(
                    CompletedExe(exerciseId, toBeCompleteExe.value.completedSet + 1)
                )

                _updateExerciseState.value = result.fold(
                    onSuccess = { res ->
                        if (res) {
                            val completedSet = toBeCompleteExe.value.completedSet
                            _toBeCompleteExe.update { exercise -> exercise.copy(completedSet = completedSet + 1) }
                            AppState.Success(true)
                        } else {
                            AppState.Error(resourceManager.getString(R.string.failed_to_save_exercise))
                        }
                    },
                    onFailure = {
                        AppState.Error(
                            it.message
                                ?: resourceManager.getString(R.string.failed_to_save_exercise)
                        )
                    }
                )
            } catch (e: Exception) {
                _updateExerciseState.value = AppState.Error(
                    e.message ?: resourceManager.getString(R.string.unknown_error_occurred)
                )
            }
        }
    }

    fun setToBeCompletedExe(exerciseUI: ExerciseUI) {
        _toBeCompleteExe.value = exerciseUI
    }

    fun onUndoSet(exerciseId: Int) {
        viewModelScope.launch {
            if (_updateExerciseState.value is AppState.Loading) return@launch
            _updateExerciseState.value = AppState.Loading

            try {
                val result = dietPlanRepository.saveExercise(
                    CompletedExe(exerciseId, toBeCompleteExe.value.completedSet - 1)
                )

                _updateExerciseState.value = result.fold(
                    onSuccess = { res ->
                        if (res) {
                            val completedSet = toBeCompleteExe.value.completedSet
                            _toBeCompleteExe.update { exercise -> exercise.copy(completedSet = completedSet - 1) }
                            AppState.Success(true)
                        } else {
                            AppState.Error(resourceManager.getString(R.string.failed_to_reset_exercise))
                        }
                    },
                    onFailure = {
                        AppState.Error(
                            it.message
                                ?: resourceManager.getString(R.string.failed_to_reset_exercise)
                        )
                    }
                )
            } catch (e: Exception) {
                _updateExerciseState.value = AppState.Error(
                    e.message ?: resourceManager.getString(R.string.unknown_error_occurred)
                )
            }
        }
    }
}