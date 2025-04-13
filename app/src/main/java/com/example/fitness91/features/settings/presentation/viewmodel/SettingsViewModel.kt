package com.example.fitness91.features.settings.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitness91.core.domain.utils.ThemeOption
import com.example.fitness91.features.settings.domain.repository.SettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository
) : ViewModel() {
    private val _themeState = MutableStateFlow(ThemeOption.SYSTEM)
    val themeState: StateFlow<ThemeOption> =
        _themeState
            .onStart { getTheme() }
            .stateIn(viewModelScope, SharingStarted.Eagerly, ThemeOption.SYSTEM)

    fun onThemeChange(selectedTheme: ThemeOption) {
        viewModelScope.launch {
            settingsRepository.setTheme(selectedTheme)
        }
    }

    private fun getTheme() {
        viewModelScope.launch {
            settingsRepository.getTheme().collect { theme -> _themeState.value = theme }
        }
    }
}