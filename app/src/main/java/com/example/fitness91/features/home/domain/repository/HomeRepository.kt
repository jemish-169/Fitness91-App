package com.example.fitness91.features.home.domain.repository

import com.example.fitness91.features.home.domain.model.DashData
import com.example.fitness91.features.home.domain.model.User

interface HomeRepository {
    suspend fun getUser(): Result<User>
    suspend fun getDashBoardData(): Result<DashData>
}