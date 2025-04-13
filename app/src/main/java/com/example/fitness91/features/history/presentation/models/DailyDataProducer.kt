package com.example.fitness91.features.history.presentation.models

import com.patrykandpatrick.vico.core.entry.ChartEntryModelProducer

data class DailyDataProducer(
    val gymExeModel : ChartEntryModelProducer,
    val faceExeModel : ChartEntryModelProducer,
    val dietPlanModel : ChartEntryModelProducer
)