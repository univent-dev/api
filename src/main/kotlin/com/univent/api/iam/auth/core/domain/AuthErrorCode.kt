package com.univent.api.iam.auth.core.domain

import com.univent.api.common.exception.CustomExceptionCode
import com.univent.api.common.exception.ErrorStatus

enum class AuthErrorCode(
    override val status: ErrorStatus,
    override val message: String
) : CustomExceptionCode {
    AUTH_OAUTH_ID_EMPTY(ErrorStatus.BAD_REQUEST, "OAuth ID는 비어 있을 수 없습니다."),
    AUTH_USER_ID_EMPTY(ErrorStatus.BAD_REQUEST, "사용자 ID는 비어 있을 수 없습니다."),
    AUTH_USER_NOT_FOUND(ErrorStatus.NOT_FOUND, "인증된 사용자를 찾을 수 없습니다."),
    AUTH_USER_ALREADY_EXISTS(ErrorStatus.CONFLICT, "이미 인증된 사용자가 존재합니다."),
    INVALID_OAUTH_ID(ErrorStatus.BAD_REQUEST, "유효하지 않은 OAuth ID입니다.")
}