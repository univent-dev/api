package com.univent.api.article.scrap.domain

import com.univent.api.common.exception.CustomExceptionCode
import com.univent.api.common.exception.ErrorStatus

enum class ScrapErrorCode(
    override val status: ErrorStatus,
    override val message: String
) : CustomExceptionCode {
    SCRAP_NOT_FOUND(
        status = ErrorStatus.NOT_FOUND,
        message = "스크랩이 존재하지 않습니다."
    ),
     SCRAP_ALREADY_EXISTS(
        status = ErrorStatus.CONFLICT,
        message = "이미 스크랩이 존재합니다."
    ),
     ARTICLE_NOT_FOUND(
        status = ErrorStatus.NOT_FOUND,
        message = "게시글이 존재하지 않습니다."
    ),
     USER_NOT_FOUND(
        status = ErrorStatus.NOT_FOUND,
        message = "사용자가 존재하지 않습니다."
    )
}
