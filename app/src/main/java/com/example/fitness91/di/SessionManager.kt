package com.example.fitness91.di

import io.github.jan.supabase.gotrue.Auth
import io.github.jan.supabase.gotrue.SessionStatus
import io.github.jan.supabase.gotrue.user.UserInfo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SessionManager @Inject constructor(
    private val supabaseAuth: Auth
) {

    private val _currentUser = MutableStateFlow<UserInfo?>(null)

    private val sessionReady = MutableStateFlow(false)

    init {
        preloadSession()
    }

    private fun preloadSession() {
        CoroutineScope(Dispatchers.IO).launch {
            supabaseAuth.sessionStatus.collectLatest { status ->
                when (status) {
                    is SessionStatus.Authenticated -> {
                        _currentUser.value = supabaseAuth.currentUserOrNull()
                        sessionReady.value = true
                    }

                    is SessionStatus.NotAuthenticated -> {
                        _currentUser.value = null
                        sessionReady.value = true
                    }

                    SessionStatus.NetworkError -> {
                        sessionReady.value = true
                    }

                    SessionStatus.LoadingFromStorage -> {
                        sessionReady.value = false
                    }
                }
            }
        }
    }

    suspend fun waitForSession(): UserInfo? {
        while (!sessionReady.value) {
            delay(50)
        }
        return _currentUser.value
    }
}
