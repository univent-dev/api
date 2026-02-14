package com.univent.api.common.exception

interface CustomExceptionCode {
    val status: ErrorStatus
    val code: String
    val message: String
}
