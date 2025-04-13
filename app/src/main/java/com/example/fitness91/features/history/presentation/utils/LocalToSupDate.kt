package com.example.fitness91.features.history.presentation.utils

import kotlinx.datetime.Month
import kotlinx.datetime.number
import java.time.LocalDate


fun LocalDate.toSupDate(): Pair<String, String> {
    val start = "${this.year}-${this.month.number}-01"
    val end: String = if (this.month == Month(12))
        "${this.year + 1}-01-01"
    else
        "${this.year}-${this.month.number + 1}-01"

    return Pair(start, end)
}