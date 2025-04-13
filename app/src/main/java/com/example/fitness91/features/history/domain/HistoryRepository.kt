package com.example.fitness91.features.history.domain

import com.example.fitness91.features.history.domain.model.DailyData

interface HistoryRepository {
    suspend fun getMonthData(dateRange: Pair<String, String>): Result<List<DailyData>>
    suspend fun getYearList(): List<Int>
    suspend fun getMonthList(): List<String>
}