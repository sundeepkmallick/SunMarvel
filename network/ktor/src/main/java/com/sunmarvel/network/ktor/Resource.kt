package com.sunmarvel.network.ktor

sealed class Resource<out T>{
    data class Success<out T>(val data: T) : Resource<T>()
    data class Error(val data: Any) : Resource<Nothing>()
    //data class Error(val resourceError: ResourceError) : Resource<Nothing>()
}

enum class ResourceError {
    UNAUTHORIZED,
    FORBIDDEN,
    NOT_FOUND,
    BAD_REQUEST,
    INTERNAL_SERVER_ERROR,
    UNKNOWN_NETWORK_ERROR,
    SOCKET_TIMEOUT_ERROR,
    UNKNOWN_ERROR,
    NO_INTERNET_CONNECTION,
}
