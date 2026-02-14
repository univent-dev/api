package com.univent.api.iam.user.domain

import com.univent.api.common.exception.CustomExceptionCode
import com.univent.api.common.exception.ErrorStatus

enum class UserErrorCode(
    override val status: ErrorStatus,
    override val message: String
) : CustomExceptionCode {
    USER_INVALID_EMAIL_FORMAT(ErrorStatus.BAD_REQUEST, "올바르지 않은 이메일 형식입니다."),
    USER_EMAIL_EMPTY(ErrorStatus.BAD_REQUEST, "이메일은 필수값입니다."),
    USER_NOT_FOUND(ErrorStatus.NOT_FOUND, "존재하지 않는 회원입니다."),
    USER_DUPLICATE_EMAIL(ErrorStatus.CONFLICT, "이미 사용 중인 이메일입니다.")
}
