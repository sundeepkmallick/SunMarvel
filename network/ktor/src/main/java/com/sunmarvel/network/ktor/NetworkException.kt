package com.sunmarvel.network.ktor

sealed class NetworkException(message: String? = null, cause: Exception? = null): Exception(message, cause) {
    data class UnAuthorizedException(override val message: String, override val cause: Exception): NetworkException(message, cause)
    data class ForbiddenException(override val message: String, override val cause: Exception): NetworkException(message, cause)
    data class NotFoundException(override val message: String, override val cause: Exception): NetworkException(message, cause)
    data class BadRequestException(override val message: String, override val cause: Exception): NetworkException(message, cause)
    data class InternalServerException(override val message: String, override val cause: Exception): NetworkException(message, cause)
    data class UnknownException(override val message: String, override val cause: Exception): NetworkException(message, cause)
    data class NoInternetException(override val message: String, override val cause: Exception): NetworkException(message, cause)
    data class SocketTimeoutException(override val message: String, override val cause: Exception): NetworkException(message, cause)
    data class UnknownHostException(override val message: String, override val cause: Exception): NetworkException(message, cause)
    data class ConnectException(override val message: String, override val cause: Exception): NetworkException(message, cause)
    data class SSLHandshakeException(override val message: String, override val cause: Exception): NetworkException(message, cause)
    data class SSLProtocolException(override val message: String, override val cause: Exception): NetworkException(message, cause)
    data class SSLException(override val message: String, override val cause: Exception): NetworkException(message, cause)
    data class SSLPeerUnverifiedException(override val message: String, override val cause: Exception): NetworkException(message, cause)
    data class SSLSessionExpiredException(override val message: String, override val cause: Exception): NetworkException(message, cause)
    data class UnknownNetworkErrorException(override val message: String, override val cause: Exception): NetworkException(message, cause)
}