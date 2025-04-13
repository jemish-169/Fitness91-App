package com.example.fitness91.features.home.presentation.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitness91.common.domain.state.AppState
import com.example.fitness91.features.home.domain.model.User
import com.example.fitness91.features.home.domain.repository.HomeRepository
import com.example.fitness91.features.home.presentation.models.DashDataUI
import com.example.fitness91.features.home.presentation.models.toDashDataUI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeRepository: HomeRepository
) : ViewModel() {

    private val _userState = MutableStateFlow(User())
    val userState: StateFlow<User> =
        _userState
            .onStart { getUser() }
            .stateIn(viewModelScope, SharingStarted.Eagerly, User())

    private val _dashBoardData = MutableStateFlow<AppState<DashDataUI, String>>(AppState.Idle)
    val dashBoardData: StateFlow<AppState<DashDataUI, String>> =
        _dashBoardData
            .onStart { getDashBoardData(false) }
            .stateIn(viewModelScope, SharingStarted.Eagerly, AppState.Idle)

    val isRefresh = mutableStateOf(false)

    private fun getUser() {
        viewModelScope.launch {
            homeRepository.getUser().fold(
                onFailure = { },
                onSuccess = { _userState.value = it }
            )
        }
    }

    fun getDashBoardData(isFromRefresh: Boolean = true) {
        viewModelScope.launch {
            _dashBoardData.value = AppState.Loading
            if (isFromRefresh) isRefresh.value = true
            homeRepository.getDashBoardData().fold(
                onFailure = { _dashBoardData.value = AppState.Success(DashDataUI()) },
                onSuccess = { _dashBoardData.value = AppState.Success(it.toDashDataUI()) }
            )
            if (isFromRefresh) isRefresh.value = false
        }
    }
}