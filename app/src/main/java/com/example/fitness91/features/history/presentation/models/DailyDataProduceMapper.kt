package com.example.fitness91.features.history.presentation.models

import com.example.fitness91.features.history.domain.model.DailyData
import com.patrykandpatrick.vico.core.entry.FloatEntry

fun List<DailyData>.toFloatEntries(selector: DailyData.() -> Float): List<FloatEntry> {
    return map { item ->
        FloatEntry(
            item.date.split("-")[2].toFloat(),
            item.selector()
        )
    }
}