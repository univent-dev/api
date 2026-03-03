package com.univent.api.article.tag.domain

import com.univent.api.common.exception.CustomExceptionCode
import com.univent.api.common.exception.ErrorStatus

enum class TagErrorCode(
    override val status: ErrorStatus,
    override val message: String
) : CustomExceptionCode {
    NOT_FOUND(ErrorStatus.NOT_FOUND, "태그를 찾을 수 없습니다."),
    ALREADY_EXIST(ErrorStatus.BAD_REQUEST, "이미 존재하는 태그입니다."),
    TAG_NAME_EMPTY(ErrorStatus.BAD_REQUEST, "태그 이름은 비어 있을 수 없습니다."),
    TAG_NAME_TOO_LONG(ErrorStatus.BAD_REQUEST, "태그 이름은 20자 이하이어야 합니다.")
}
