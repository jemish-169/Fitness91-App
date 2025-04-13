package com.example.fitness91.features.history.data

import com.example.fitness91.R
import com.example.fitness91.core.data.utils.Functions
import com.example.fitness91.di.ResourceManager
import com.example.fitness91.di.SessionManager
import com.example.fitness91.features.history.data.mappers.toGetDailyData
import com.example.fitness91.features.history.data.models.DailyDataResponseDto
import com.example.fitness91.features.history.domain.HistoryRepository
import com.example.fitness91.features.history.domain.model.DailyData
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.rpc
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import kotlinx.datetime.Month
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale
import javax.inject.Inject

class HistoryRepositoryImpl @Inject constructor(
    private val sessionManager: SessionManager,
    private val supDatabase: Postgrest,
    private val resourceManager: ResourceManager
) : HistoryRepository {
    override suspend fun getMonthData(dateRange: Pair<String, String>): Result<List<DailyData>> {
        return withContext(Dispatchers.IO) {
            try {
                sessionManager.waitForSession()?.id?.let {
                    val gymDeffer =
                        async { getDailyData(Functions.GET_DAILY_GYM_EXE_DATA, dateRange, it) }
                    val faceDeffer =
                        async { getDailyData(Functions.GET_DAILY_FACE_EXE_DATA, dateRange, it) }
                    val dietDeffer =
                        async { getDailyData(Functions.GET_DAILY_DIET_DATA, dateRange, it) }

                    val gymRes = gymDeffer.await()
                    val faceRes = faceDeffer.await()
                    val dietRes = dietDeffer.await()

                    withContext(Dispatchers.Default) {
                        val gymMap = gymRes.associateBy({ it.date }, { it.totalSets })
                        val faceMap = faceRes.associateBy({ it.date }, { it.totalSets })
                        val dietMap = dietRes.associateBy({ it.date }, { it.totalSets })

                        val dailyDataSet = (gymMap.keys + faceMap.keys + dietMap.keys).toSet()

                        val dailyDataRes = dailyDataSet.map { date ->
                            DailyData(
                                date = date,
                                gymExe = gymMap[date] ?: 0,
                                faceExe = faceMap[date] ?: 0,
                                dietPlan = dietMap[date] ?: 0
                            )
                        }.sortedBy { it.date }

                        Result.success(dailyDataRes)
                    }
                }
                    ?: Result.failure(Exception(resourceManager.getString(R.string.user_not_found_something_went_wrong)))
            } catch (e: Exception) {
                Result.failure(Exception(resourceManager.getString(R.string.unknown_error_occurred_during_data_fetch)))
            }
        }
    }

    override suspend fun getYearList(): List<Int> {
        return withContext(Dispatchers.Default) {
            val currentYear = LocalDate.now().year
            (currentYear - 3..currentYear).toList()
        }
    }

    override suspend fun getMonthList(): List<String> {
        return withContext(Dispatchers.Default) {
            (1..12)
                .map { Month(it).getDisplayName(TextStyle.SHORT, Locale.getDefault()) }
        }
    }

    private suspend fun getDailyData(
        function: String,
        dateRange: Pair<String, String>,
        userId: String
    ): List<DailyDataResponseDto> {
        return try {
            supDatabase.rpc(
                function = function,
                dateRange.toGetDailyData(userId)
            ).decodeList<DailyDataResponseDto>()
        } catch (e: Exception) {
            emptyList()
        }
    }
}