package com.example.fitness91.features.settings.data

import com.example.fitness91.core.data.Preferences
import com.example.fitness91.core.domain.utils.ThemeOption
import com.example.fitness91.features.settings.domain.repository.SettingsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SettingsRepositoryImpl @Inject constructor(
    private val preferences: Preferences
) : SettingsRepository {
    override suspend fun setTheme(themeOption: ThemeOption) {
        return try {
            withContext(Dispatchers.IO) {
                preferences.setTheme(themeOption)
            }
        } catch (_: Exception) {
        }
    }

    override suspend fun getTheme(): Flow<ThemeOption> {
        return withContext(Dispatchers.IO) {
            preferences.getTheme()
        }
    }
}