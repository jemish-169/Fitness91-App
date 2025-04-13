package com.example.fitness91.features.auth.data.mappers

import com.example.fitness91.features.auth.data.dto.SignUpDetailDto
import com.example.fitness91.features.auth.domain.model.SignUpDetail

fun SignUpDetail.toSignUpDetailDto(id: String): SignUpDetailDto {
    return SignUpDetailDto(
        id = id,
        firstName = this.firstName.trim(),
        email = this.email.trim()
    )
}