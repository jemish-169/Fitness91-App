package com.example.fitness91.core.data

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.fitness91.R
import com.example.fitness91.core.domain.utils.ThemeOption
import com.example.fitness91.features.home.domain.model.User
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Preferences @Inject constructor(
    @ApplicationContext private val context: Context
) {

    companion object {
        private const val DATASTORE_NAME = "app_preferences"

        // DataStore instance
        private val Context.dataStore by preferencesDataStore(name = DATASTORE_NAME)

        // Keys for preferences
        private val SELECTED_THEME = stringPreferencesKey("selected_theme")
        private val IS_ONBOARDED = booleanPreferencesKey("is_onboarded")
        private val IS_REMEMBER = booleanPreferencesKey("is_remember")
        private val EMAIL = stringPreferencesKey("email")
        private val PASSWORD = stringPreferencesKey("password")
        private val FIRST_NAME = stringPreferencesKey("first_name")
    }

    // Read 'isOnboarded'
    suspend fun getIsOnboarded(): Boolean =
        context.dataStore.data.catch { exception ->
            if (exception is IOException) emit(emptyPreferences())
            else throw exception
        }.map { preferences ->
            preferences[IS_ONBOARDED] ?: false
        }.first()

    // Set 'isOnboarded'
    suspend fun setIsOnboarded(isOnboarded: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[IS_ONBOARDED] = isOnboarded
        }
    }

    // Clear all preferences based on isRemember
    suspend fun clearAllPreferences() {
        context.dataStore.edit {
            if (it[IS_REMEMBER] == true) {
                it[IS_ONBOARDED] = false
                it[FIRST_NAME] = ""
            } else {
                it.clear()
            }
        }
    }

    // Get user details
    fun getUser(): Flow<Result<User>> =
        context.dataStore.data.catch { exception ->
            if (exception is IOException) emit(emptyPreferences())
            else throw exception
        }.map { preferences ->
            val email = preferences[EMAIL] ?: ""
            val firstName = preferences[FIRST_NAME] ?: ""

            if (email.isNotEmpty() && firstName.isNotEmpty()) {
                Result.success(
                    User(
                        email = email,
                        firstName = firstName,
                    )
                )
            } else {
                Result.failure(Exception(context.getString(R.string.failure_in_getting_saved_data)))
            }
        }

    // Get Auth credentials
    fun getAuthCredentials(): Flow<Result<Pair<String, String>>> =
        context.dataStore.data.catch { exception ->
            if (exception is IOException) emit(emptyPreferences())
            else throw exception
        }.map { preferences ->
            val email = preferences[EMAIL] ?: ""
            val password = preferences[PASSWORD] ?: ""

            if (email.isNotEmpty() && password.isNotEmpty()) {
                Result.success(
                    Pair(email, password)
                )
            } else {
                Result.failure(Exception(context.getString(R.string.failure_in_getting_saved_auth_credentials)))
            }
        }

    // Set user details
    suspend fun setUser(
        isRemember: Boolean,
        email: String,
        firstName: String,
        password: String
    ) {
        context.dataStore.edit { preferences ->
            preferences[IS_REMEMBER] = isRemember
            preferences[EMAIL] = email
            preferences[PASSWORD] = password
            preferences[FIRST_NAME] = firstName
        }
    }

    suspend fun setTheme(themeOption: ThemeOption) {
        context.dataStore.edit { preferences ->
            preferences[SELECTED_THEME] = themeOption.displayName
        }
    }

    fun getTheme(): Flow<ThemeOption> {
        return context.dataStore.data.catch { exception ->
            if (exception is IOException) emit(emptyPreferences())
            else throw exception
        }.map { preferences ->
            val theme = preferences[SELECTED_THEME]
            when (theme) {
                ThemeOption.DARK.displayName -> ThemeOption.DARK
                ThemeOption.LIGHT.displayName -> ThemeOption.LIGHT
                else -> ThemeOption.SYSTEM
            }
        }
    }
}
