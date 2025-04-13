package com.example.fitness91.features.face_exe.data

import com.example.fitness91.R
import com.example.fitness91.core.data.utils.FaceExeRecordTableColumns
import com.example.fitness91.core.data.utils.FaceExeTableColumns
import com.example.fitness91.core.data.utils.Functions
import com.example.fitness91.core.data.utils.Tables
import com.example.fitness91.core.data.utils.getTodayDate
import com.example.fitness91.core.data.utils.getTodayDay
import com.example.fitness91.di.ResourceManager
import com.example.fitness91.di.SessionManager
import com.example.fitness91.features.face_exe.data.mapper.toExercise
import com.example.fitness91.features.face_exe.data.mapper.toTodayData
import com.example.fitness91.features.face_exe.data.mapper.toUpdateCompleted
import com.example.fitness91.features.face_exe.data.model.ExerciseResponseDto
import com.example.fitness91.features.face_exe.data.model.GetCompletedExeResDto
import com.example.fitness91.features.face_exe.domain.FaceExeRepository
import com.example.fitness91.features.face_exe.domain.model.CompletedExe
import com.example.fitness91.features.face_exe.domain.model.Exercise
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.query.Columns
import io.github.jan.supabase.postgrest.query.Order
import io.github.jan.supabase.postgrest.rpc
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FaceExeRepositoryImpl @Inject constructor(
    private val sessionManager: SessionManager,
    private val supDatabase: Postgrest,
    private val resourceManager: ResourceManager
) : FaceExeRepository {

    override suspend fun getTodayExercise(): Result<List<Exercise>> {
        return withContext(Dispatchers.IO) {
            try {
                val todayDay = getTodayDay()
                val exerciseList = supDatabase[Tables.FACE_EXERCISE].select {
                    filter {
                        or {
                            eq(FaceExeTableColumns.DAY, todayDay)
                            eq(FaceExeTableColumns.DAY, -1)
                        }
                    }
                    order(FaceExeTableColumns.ID, Order.ASCENDING)
                }.decodeList<ExerciseResponseDto>().map { exerciseResponseDto ->
                    exerciseResponseDto.toExercise()
                }
                Result.success(exerciseList)
            } catch (e: Exception) {
                Result.failure(Exception(resourceManager.getString(R.string.unknown_error_occurred_during_exercise_fetch)))
            }
        }
    }

    override suspend fun getTodayCompleted(): Result<List<CompletedExe>> {
        return withContext(Dispatchers.IO) {
            try {
                val todayDate = getTodayDate()
                sessionManager.waitForSession()?.id?.let {
                    val todayDataList = supDatabase[Tables.FACE_EXERCISE_RECORD].select(
                        columns = Columns.list(
                            FaceExeRecordTableColumns.EXERCISE_ID,
                            FaceExeRecordTableColumns.SETS
                        )
                    ) {
                        filter {
                            eq(FaceExeRecordTableColumns.CREATED_BY, it)
                            eq(FaceExeRecordTableColumns.CREATED_AT, todayDate)
                        }
                    }.decodeList<GetCompletedExeResDto>().map { it.toTodayData() }
                    Result.success(todayDataList)
                }
                    ?: Result.failure(Exception(resourceManager.getString(R.string.user_not_found_something_went_wrong)))
            } catch (e: Exception) {
                Result.failure(Exception(resourceManager.getString(R.string.unknown_error_occurred_during_today_data_fetch)))
            }
        }
    }

    override suspend fun saveExercise(value: CompletedExe): Result<Boolean> {
        return withContext(Dispatchers.IO) {
            try {
                sessionManager.waitForSession()?.id?.let {
                    val isSuccess = supDatabase.rpc(
                        Functions.UPSERT_EXERCISE_RECORD,
                        value.toUpdateCompleted(
                            it,
                            Tables.FACE_EXERCISE_RECORD
                        )
                    ).decodeAs<Boolean>()
                    Result.success(isSuccess)
                }
                    ?: Result.failure(Exception(resourceManager.getString(R.string.user_not_found_something_went_wrong)))
            } catch (e: Exception) {
                Result.failure(Exception(resourceManager.getString(R.string.unknown_error_occurred_during_data_save)))
            }
        }
    }
}