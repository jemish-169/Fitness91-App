package com.example.fitness91.features.diet_plan.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CompleteExeRequest(
    @SerialName("p_id")
    val exerciseId: Int,

    @SerialName("p_sets")
    val sets: Int,

    @SerialName("p_created_by")
    val createdBy: String,

    @SerialName("p_table_name")
    val tableName: String
)