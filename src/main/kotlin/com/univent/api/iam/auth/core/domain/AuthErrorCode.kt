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
    INVALID_OAUTH_ID(ErrorStatus.BAD_REQUEST, "유효하지 않은 OAuth ID입니다."),
    AUTH_INVALID_REFRESH_TOKEN(ErrorStatus.BAD_REQUEST, "유효하지 않은 리프레시 토큰입니다."),
    AUTH_INVALID_OAUTH_PROVIDER(ErrorStatus.BAD_REQUEST, "유효하지 않은 OAuth 제공자입니다."),
    AUTH_INVALID_OAUTH_TOKEN(ErrorStatus.BAD_REQUEST, "유효하지 않은 OAuth 토큰입니다."),
    AUTH_OAUTH_USER_NOT_FOUND(ErrorStatus.NOT_FOUND, "OAuth 사용자 정보를 찾을 수 없습니다."),
    AUTH_OAUTH_USER_EMAIL_NOT_FOUND(ErrorStatus.NOT_FOUND, "OAuth 사용자 이메일 정보를 찾을 수 없습니다."),
    AUTH_PASSWORD_ENCODING_FAILED(ErrorStatus.INTERNAL_ERROR, "비밀번호 인코딩에 실패했습니다."),
    AUTH_ORGANIZATION_INVALID_ACCOUNT_ID_LENGTH(ErrorStatus.BAD_REQUEST, "계정 ID는 4자 이상 20자 이하이어야 합니다."),
    AUTH_ORGANIZATION_INVALID_ACCOUNT_ID_FORMAT(ErrorStatus.BAD_REQUEST, "계정 ID는 소문자, 숫자, 밑줄(_)만 사용할 수 있습니다."),
    AUTH_ORGANIZATION_INVALID_PASSWORD_LENGTH(ErrorStatus.BAD_REQUEST, "비밀번호는 10자 이상 30자 이하이어야 합니다."),
    AUTH_ORGANIZATION_INVALID_PASSWORD_FORMAT(ErrorStatus.BAD_REQUEST, "비밀번호는 영문과 숫자를 모두 포함해야 합니다."),
    AUTH_ORGANIZATION_NOT_FOUND(ErrorStatus.NOT_FOUND, "인증된 조직을 찾을 수 없습니다."),
    AUTH_INVALID_PASSWORD(ErrorStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다."),
    AUTH_ORGANIZATION_ACCOUNT_ID_ALREADY_EXISTS(ErrorStatus.CONFLICT, "이미 존재하는 계정 ID입니다."),
    AUTH_ORGANIZATION_INVALID_ACCESS_TOKEN(ErrorStatus.UNAUTHORIZED, "유효하지 않은 액세스 토큰입니다."),
    AUTH_ORGANIZATION_WITHDRAW_FAILED(ErrorStatus.INTERNAL_ERROR, "조직 탈퇴 처리에 실패했습니다."),
    AUTH_ORGANIZATION_INVALID_PASSWORD(ErrorStatus.BAD_REQUEST, "유효하지 않은 비밀번호입니다."),
}