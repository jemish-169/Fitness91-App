package com.example.fitness91.features.home.presentation.models

import com.example.fitness91.features.home.domain.model.DashData

fun DashData.toDashDataUI(): DashDataUI {
    return DashDataUI(
        gymCompleted = this.gymCompleted,
        gymTarget = this.gymTarget,
        dietCompleted = this.dietCompleted,
        dietTarget = this.dietTarget,
        faceCompleted = this.faceCompleted,
        faceTarget = this.faceTarget,
        gymPercentage = if (this.gymTarget == 0) 0 else ((this.gymCompleted.toDouble() / this.gymTarget.toDouble()) * 100.0).toInt(),
        dietPercentage = if (this.dietTarget == 0) 0 else ((this.dietCompleted.toDouble() / this.dietTarget.toDouble()) * 100.0).toInt(),
        facePercentage = if (this.faceTarget == 0) 0 else ((this.faceCompleted.toDouble() / this.faceTarget.toDouble()) * 100.0).toInt()
    )
}