package com.univent.api.common.exception

class CustomException(
    val exceptionCode: CustomExceptionCode,
    val data: Any? = null
) : RuntimeException(exceptionCode.message)
