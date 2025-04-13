package com.example.fitness91.di

import com.example.fitness91.core.data.Preferences
import com.example.fitness91.features.auth.data.AuthRepositoryImpl
import com.example.fitness91.features.auth.domain.repository.AuthRepository
import com.example.fitness91.features.diet_plan.data.DietPlanRepositoryImpl
import com.example.fitness91.features.diet_plan.domain.DietPlanRepository
import com.example.fitness91.features.face_exe.data.FaceExeRepositoryImpl
import com.example.fitness91.features.face_exe.domain.FaceExeRepository
import com.example.fitness91.features.gym_train.data.GymTrainRepositoryImpl
import com.example.fitness91.features.gym_train.domain.GymTrainRepository
import com.example.fitness91.features.history.data.HistoryRepositoryImpl
import com.example.fitness91.features.history.domain.HistoryRepository
import com.example.fitness91.features.home.data.HomeRepositoryImpl
import com.example.fitness91.features.home.domain.repository.HomeRepository
import com.example.fitness91.features.settings.data.SettingsRepositoryImpl
import com.example.fitness91.features.settings.domain.repository.SettingsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.jan.supabase.gotrue.Auth
import io.github.jan.supabase.postgrest.Postgrest
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideAuthRepository(
        supAuth: Auth,
        sessionManager: SessionManager,
        supData: Postgrest,
        preferences: Preferences,
        resourceManager: ResourceManager
    ): AuthRepository =
        AuthRepositoryImpl(supAuth, sessionManager, supData, preferences, resourceManager)

    @Provides
    @Singleton
    fun provideHomeRepository(
        sessionManager: SessionManager,
        supData: Postgrest,
        preferences: Preferences,
        resourceManager: ResourceManager
    ): HomeRepository = HomeRepositoryImpl(sessionManager, supData, preferences, resourceManager)

    @Provides
    @Singleton
    fun provideHistoryRepository(
        sessionManager: SessionManager,
        supData: Postgrest,
        resourceManager: ResourceManager
    ): HistoryRepository =
        HistoryRepositoryImpl(sessionManager, supData, resourceManager)

    @Provides
    @Singleton
    fun provideGymTrainRepository(
        sessionManager: SessionManager,
        supData: Postgrest,
        resourceManager: ResourceManager
    ): GymTrainRepository =
        GymTrainRepositoryImpl(sessionManager, supData, resourceManager)

    @Provides
    @Singleton
    fun provideFaceExpRepository(
        sessionManager: SessionManager,
        supData: Postgrest,
        resourceManager: ResourceManager
    ): FaceExeRepository =
        FaceExeRepositoryImpl(sessionManager, supData, resourceManager)

    @Provides
    @Singleton
    fun provideDietPlanRepository(
        sessionManager: SessionManager,
        supData: Postgrest,
        resourceManager: ResourceManager
    ): DietPlanRepository =
        DietPlanRepositoryImpl(sessionManager, supData, resourceManager)

    @Provides
    @Singleton
    fun provideSettingsRepository(
        preferences: Preferences
    ): SettingsRepository = SettingsRepositoryImpl(preferences)
}