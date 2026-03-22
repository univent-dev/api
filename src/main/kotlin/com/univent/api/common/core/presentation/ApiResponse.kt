package com.univent.api.common.core.presentation

import com.fasterxml.jackson.annotation.JsonInclude

data class ApiResponse<T>(
    val success: Boolean,
    val message: String,

    @JsonInclude(JsonInclude.Include.NON_NULL)
    val data: T? = null
) {
    companion object {
        fun <T> success(data: T? = null, message: String = "Success"): ApiResponse<T> {
            return ApiResponse(
                success = true,
                message = message,
                data = data
            )
        }

        fun fail(message: String): ApiResponse<Unit> {
            return ApiResponse(
                success = false,
                message = message,
                data = null
            )
        }
    }
}
