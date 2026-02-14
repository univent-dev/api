package com.univent.api.common.exception

import com.univent.api.common.core.presentation.ApiResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(CustomException::class)
    fun handleCustomException(e: CustomException): ResponseEntity<ApiResponse<Any?>> {
        val exceptionCode = e.exceptionCode

        return ResponseEntity
            .status(exceptionCode.status.httpStatus)
            .body(
                ApiResponse(
                    success = false,
                    code = exceptionCode.code,
                    message = exceptionCode.message,
                    data = e.data
                )
            )
    }

    @ExceptionHandler(Exception::class)
    fun handleGeneralException(e: Exception): ResponseEntity<ApiResponse<Unit>> {
        return ResponseEntity
            .status(500)
            .body(ApiResponse.fail("500", "예상치 못한 서버 오류가 발생했습니다. - ${e.message}"))
    }
}
