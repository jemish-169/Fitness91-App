package com.example.fitness91.features.history.data.mappers

import com.example.fitness91.features.history.data.models.GetDailySalesDto

fun Pair<String, String>.toGetDailyData(userId: String): GetDailySalesDto {
    return GetDailySalesDto(
        startDate = this.first,
        endDate = this.second,
        id = userId
    )
}