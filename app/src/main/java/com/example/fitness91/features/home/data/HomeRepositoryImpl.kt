package com.example.fitness91.features.home.data

import android.util.Log
import com.example.fitness91.R
import com.example.fitness91.core.data.Preferences
import com.example.fitness91.core.data.utils.Functions
import com.example.fitness91.core.data.utils.getTodayDay
import com.example.fitness91.di.ResourceManager
import com.example.fitness91.di.SessionManager
import com.example.fitness91.features.home.data.models.DashDataDto
import com.example.fitness91.features.home.data.models.GetDashData
import com.example.fitness91.features.home.data.models.toDashData
import com.example.fitness91.features.home.domain.model.DashData
import com.example.fitness91.features.home.domain.model.User
import com.example.fitness91.features.home.domain.repository.HomeRepository
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.rpc
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val sessionManager: SessionManager,
    private val supDatabase: Postgrest,
    private val preferences: Preferences,
    private val resourceManager: ResourceManager
) : HomeRepository {

    override suspend fun getUser(): Result<User> {
        return try {
            withContext(Dispatchers.IO) {
                preferences.getUser().first()
            }
        } catch (e: Exception) {
            Result.failure(Exception(resourceManager.getString(R.string.unknown_error_occurred_during_user_data_fetch)))
        }
    }

    override suspend fun getDashBoardData(): Result<DashData> {
        return withContext(Dispatchers.IO) {
            try {
                val todayDay = getTodayDay()
                sessionManager.waitForSession()?.id?.let {
                    val dashDataDto = supDatabase.rpc(
                        Functions.GET_DASHBOARD_DATA,
                        GetDashData(pDay = todayDay, pCreatedBy = it)
                    ).decodeSingle<DashDataDto>()
                    Log.e("TAG", "getDashBoardData: $dashDataDto")
                    Result.success(dashDataDto.toDashData())
                }
                    ?: Result.failure(Exception(resourceManager.getString(R.string.user_not_found_something_went_wrong)))
            } catch (e: Exception) {
                Result.failure(Exception(resourceManager.getString(R.string.unknown_error_occurred_during_dash_data_fetch)))
            }
        }
    }
}