package com.example.fitness91.features.home.data.models

import com.example.fitness91.features.home.domain.model.DashData

fun DashDataDto.toDashData(): DashData {
    return DashData(
        gymCompleted = this.gymCompleted,
        gymTarget = this.gymTarget,
        dietCompleted = this.dietCompleted,
        dietTarget = this.dietTarget,
        faceCompleted = this.faceCompleted,
        faceTarget = this.faceTarget,
    )
}