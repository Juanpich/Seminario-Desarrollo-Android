package ar.edu.unicen.seminario.ddl.models

sealed class Result<out T> {
    data class Success<T>(val data: T) : Result<T>()
    data class Error(val type: ErrorType) : Result<Nothing>()
}

enum class ErrorType {
    NETWORK,
    TIMEOUT,
    SERVER,
    EMPTY_RESPONSE,
    UNKNOWN
}
