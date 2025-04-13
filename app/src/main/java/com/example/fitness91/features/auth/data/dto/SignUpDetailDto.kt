package com.example.fitness91.features.auth.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SignUpDetailDto(

    @SerialName("id")
    val id: String,

    @SerialName("first_name")
    val firstName: String = "",

    @SerialName("email")
    val email: String = ""
)
