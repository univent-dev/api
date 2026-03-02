package com.univent.api.article.article.domain

import com.univent.api.common.exception.CustomExceptionCode
import com.univent.api.common.exception.ErrorStatus

enum class ArticleErrorCode(
    override val status: ErrorStatus,
    override val message: String
) : CustomExceptionCode {
    ARTICLE_NOT_FOUND(
        status = ErrorStatus.NOT_FOUND,
        message = "존재하지 않는 게시글입니다."
    ),
    ARTICLE_ALREADY_DELETED(
        status = ErrorStatus.BAD_REQUEST,
        message = "이미 삭제된 게시글입니다."
    ),
    ARTICLE_ALREADY_EXISTED(
        status = ErrorStatus.BAD_REQUEST,
        message = "이미 존재하는 게시글입니다."
    ),
    ARTICLE_TITLE_EMPTY(
        status = ErrorStatus.BAD_REQUEST,
        message = "게시글 제목은 비어있을 수 없습니다."
    ),
    ARTICLE_CONTENT_EMPTY(
        status = ErrorStatus.BAD_REQUEST,
        message = "게시글 내용은 비어있을 수 없습니다."
    ),
    ARTICLE_ORGANIZATION_EMPTY(
        status = ErrorStatus.BAD_REQUEST,
        message = "게시글은 소속된 조직이 있어야 합니다."
    ),
    ARTICLE_LOCATION_EMPTY(
        status = ErrorStatus.BAD_REQUEST,
        message = "게시글은 위치 정보가 있어야 합니다."
    ),
    ARTICLE_START_AT_EXCEEDS_END_AT(
        status = ErrorStatus.BAD_REQUEST,
        message = "게시글의 시작 시간은 종료 시간보다 늦을 수 없습니다."
    ),
    ARTICLE_SCRAP_COUNT_NEGATIVE(
        status = ErrorStatus.BAD_REQUEST,
        message = "게시글의 스크랩 수는 음수일 수 없습니다."
    ),
    ARTICLE_VIEW_COUNT_NEGATIVE(
        status = ErrorStatus.BAD_REQUEST,
        message = "게시글의 조회 수는 음수일 수 없습니다."
    ),
    ARTICLE_MEDIA_MAX_IMAGES_EXCEEDED(
        status = ErrorStatus.BAD_REQUEST,
        message = "게시글에 첨부할 수 있는 미디어는 최대 10개입니다."
    )
}
