package com.example.fitness91.features.face_exe.data.mapper

import com.example.fitness91.features.face_exe.data.model.CompleteExeRequest
import com.example.fitness91.features.face_exe.data.model.GetCompletedExeResDto
import com.example.fitness91.features.face_exe.domain.model.CompletedExe

fun GetCompletedExeResDto.toTodayData(): CompletedExe {
    return CompletedExe(
        sets = this.sets,
        exerciseId = this.exerciseId
    )
}

fun CompletedExe.toUpdateCompleted(uId: String, tableName: String): CompleteExeRequest {
    return CompleteExeRequest(
        sets = this.sets,
        exerciseId = this.exerciseId,
        createdBy = uId,
        tableName = tableName
    )
}