package com.univent.api.media.domain

import com.univent.api.common.exception.CustomExceptionCode
import com.univent.api.common.exception.ErrorStatus

enum class MediaErrorCode(
    override val status: ErrorStatus,
    override val message: String
) : CustomExceptionCode {
    MEDIA_PATH_EMPTY(ErrorStatus.BAD_REQUEST, "미디어 경로는 필수 입니다."),
    MEDIA_INVALID_PATH(ErrorStatus.BAD_REQUEST, "유효하지 않은 미디어 경로입니다. https://{도메인}/images/{articleId}/{mediaId}.{확장자} 형식이어야 합니다."),
    MEDIA_ORDER_EMPTY(ErrorStatus.BAD_REQUEST, "미디어 순서는 필수 입니다."),
    MEDIA_ARTICLE_ID_EMPTY(ErrorStatus.BAD_REQUEST, "미디어가 속한 게시글 ID는 필수 입니다.")
}
