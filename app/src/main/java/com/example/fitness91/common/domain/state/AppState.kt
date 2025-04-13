package com.example.fitness91.common.domain.state


sealed interface AppState<out S, out E> {
    data object Idle : AppState<Nothing, Nothing>
    data object Loading : AppState<Nothing, Nothing>
    data class Success<out S>(val message: S) : AppState<S, Nothing>
    data class Error<out E>(val error: E) : AppState<Nothing, E>
}