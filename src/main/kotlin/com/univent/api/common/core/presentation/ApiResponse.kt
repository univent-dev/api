package com.univent.api.common.core.presentation

import com.fasterxml.jackson.annotation.JsonInclude

data class ApiResponse<T>(
    val success: Boolean,
    val code: String,
    val message: String,

    @JsonInclude(JsonInclude.Include.NON_NULL)
    val data: T? = null
) {
    companion object {
        fun <T> success(data: T? = null, message: String = "Success"): ApiResponse<T> {
            return ApiResponse(
                success = true,
                code = "COMMON-200",
                message = message,
                data = data
            )
        }

        fun fail(code: String, message: String): ApiResponse<Unit> {
            return ApiResponse(
                success = false,
                code = code,
                message = message,
                data = null
            )
        }
    }
}
