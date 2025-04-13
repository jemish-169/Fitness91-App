package com.example.fitness91.features.auth.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class SignUpDetail(
    val firstName: String = "",
    val email: String = "",
    val password: String = "",
    val isRemember: Boolean = true
)
