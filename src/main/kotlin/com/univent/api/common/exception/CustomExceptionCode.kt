package com.univent.api.common.exception

interface CustomExceptionCode {
    val status: ErrorStatus
    val message: String
}
