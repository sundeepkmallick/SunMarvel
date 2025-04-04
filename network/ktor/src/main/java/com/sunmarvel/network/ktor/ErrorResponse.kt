package com.sunmarvel.network.ktor

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class ErrorResponse(
    @SerializedName("message", alternate= ["description"])
    val message: String,

    @SerializedName("code", alternate= ["errorCode"])
    val code: String,

    @SerializedName("errors")
    val errors: List<Error>? = null,

    val httpStatusCode: Int = -1
)

@Serializable
data class Error(
    @SerializedName("code")
    val code: String? = null,

    @SerializedName("field")
    val field: String? = null,

    @SerializedName("message")
    val message: String? = null
)