package com.univent.api.iam.organization.domain

import com.univent.api.common.exception.CustomExceptionCode
import com.univent.api.common.exception.ErrorStatus

enum class OrganizationErrorCode(
    override val status: ErrorStatus,
    override val message: String
) : CustomExceptionCode  {
    ORGANIZATION_INVALID_NAME_LENGTH(ErrorStatus.BAD_REQUEST, "조직 이름은 1자 이상 63자 이하이어야 합니다."),
    ORGANIZATION_INVALID_CONTACT_FORMAT(ErrorStatus.BAD_REQUEST, "연락처는 '010-xxxx-xxxx' 형식이어야 합니다."),
    ORGANIZATION_NOT_FOUND(ErrorStatus.NOT_FOUND, "조직을 찾을 수 없습니다.")
}
