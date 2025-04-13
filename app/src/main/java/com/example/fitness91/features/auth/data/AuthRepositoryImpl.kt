package com.example.fitness91.features.auth.data

import com.example.fitness91.R
import com.example.fitness91.core.data.Preferences
import com.example.fitness91.core.data.utils.AuthTableColumns
import com.example.fitness91.core.data.utils.Tables
import com.example.fitness91.di.ResourceManager
import com.example.fitness91.di.SessionManager
import com.example.fitness91.features.auth.data.dto.SignUpDetailDto
import com.example.fitness91.features.auth.data.mappers.toSignUpDetailDto
import com.example.fitness91.features.auth.domain.model.SignUpDetail
import com.example.fitness91.features.auth.domain.repository.AuthRepository
import io.github.jan.supabase.gotrue.Auth
import io.github.jan.supabase.gotrue.providers.builtin.Email
import io.github.jan.supabase.postgrest.Postgrest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val supAuth: Auth,
    private val sessionManager: SessionManager,
    private val supDatabase: Postgrest,
    private val preferences: Preferences,
    private val resourceManager: ResourceManager
) : AuthRepository {

    override suspend fun signIn(signUpDetail: SignUpDetail): Result<String> {
        return withContext(Dispatchers.IO) {
            try {
                supAuth.signInWith(Email) {
                    email = signUpDetail.email.trim()
                    password = signUpDetail.password.trim()
                }

                // Need double validation because some-time session is not loaded even user is logged in
                val res = loadUserData(signUpDetail)
                res.fold(
                    onSuccess = { return@withContext res },
                    onFailure = {
                        if (it.message?.equals(resourceManager.getString(R.string.user_not_found_something_went_wrong)) == true) {
                            delay(100)
                            return@withContext loadUserData(signUpDetail)
                        } else return@withContext res
                    }
                )
            } catch (e: Exception) {
                Result.failure(Exception(resourceManager.getString(R.string.unknown_error_occurred_during_sign_in)))
            }
        }
    }

    override suspend fun signUp(signUpDetail: SignUpDetail): Result<String> {
        return withContext(Dispatchers.IO) {
            try {
                supAuth.signUpWith(Email) {
                    email = signUpDetail.email.trim()
                    password = signUpDetail.password.trim()
                }

                // Need double validation because some-time session is not loaded even user is authenticated
                val res = setUserData(signUpDetail = signUpDetail)
                res.fold(
                    onSuccess = { return@withContext res },
                    onFailure = {
                        if (it.message?.equals(resourceManager.getString(R.string.user_not_found_something_went_wrong)) == true) {
                            delay(100)
                            return@withContext setUserData(signUpDetail = signUpDetail)
                        } else return@withContext res
                    }
                )
            } catch (e: Exception) {
                Result.failure(Exception(resourceManager.getString(R.string.unknown_error_occurred_during_sign_up)))
            }
        }
    }

    override suspend fun signOut(): Result<String> {
        return withContext(Dispatchers.IO) {
            try {
                supAuth.signOut()
                preferences.clearAllPreferences()
                Result.success(resourceManager.getString(R.string.sign_out_successfully))
            } catch (e: Exception) {
                Result.failure(Exception(resourceManager.getString(R.string.unknown_error_occurred_during_sign_out)))
            }
        }
    }

    override suspend fun getAuthCredentials(): Result<Pair<String, String>> {
        return try {
            withContext(Dispatchers.IO) {
                preferences.getAuthCredentials().first()
            }
        } catch (e: Exception) {
            Result.failure(Exception(resourceManager.getString(R.string.unknown_error_occurred_in_getting_auth_credentials)))
        }
    }

    private fun isUserValid(user: SignUpDetailDto): Boolean {
        return user.firstName.isNotEmpty() && user.email.isNotEmpty()
    }

    private suspend fun loadUserData(signUpDetail: SignUpDetail): Result<String> {
        return try {
            sessionManager.waitForSession()?.id?.let {
                val res = supDatabase[Tables.USERS].select {
                    filter {
                        eq(AuthTableColumns.ID, it)
                    }
                }.decodeSingle<SignUpDetailDto>()

                if (isUserValid(res)) {
                    preferences.setUser(
                        isRemember = signUpDetail.isRemember,
                        email = res.email,
                        firstName = res.firstName,
                        password = signUpDetail.password
                    )
                    preferences.setIsOnboarded(true)
                    Result.success(resourceManager.getString(R.string.sign_in_was_successful))
                } else Result.failure(Exception(resourceManager.getString(R.string.user_has_no_data_contact_support)))
            }
                ?: Result.failure(Exception(resourceManager.getString(R.string.user_not_found_something_went_wrong)))
        } catch (e: Exception) {
            Result.failure(Exception(resourceManager.getString(R.string.unknown_error_occurred_during_sign_in)))
        }
    }

    private suspend fun setUserData(signUpDetail: SignUpDetail): Result<String> {
        return try {
            sessionManager.waitForSession()?.id?.let {
                supDatabase[Tables.USERS].insert(
                    signUpDetail.toSignUpDetailDto(id = it)
                )
                preferences.setUser(
                    isRemember = signUpDetail.isRemember,
                    email = signUpDetail.email,
                    firstName = signUpDetail.firstName,
                    password = signUpDetail.password,
                )
                preferences.setIsOnboarded(true)
                Result.success(resourceManager.getString(R.string.sign_up_was_successful))
            }
                ?: Result.failure(Exception(resourceManager.getString(R.string.user_not_found_something_went_wrong)))
        } catch (e: Exception) {
            Result.failure(Exception(resourceManager.getString(R.string.unknown_error_occurred_during_sign_up)))
        }
    }
}