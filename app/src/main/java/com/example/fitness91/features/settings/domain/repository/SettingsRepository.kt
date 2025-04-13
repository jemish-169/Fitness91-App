package com.example.fitness91.features.settings.domain.repository

import com.example.fitness91.core.domain.utils.ThemeOption
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    suspend fun setTheme(themeOption: ThemeOption)
    suspend fun getTheme(): Flow<ThemeOption>
}