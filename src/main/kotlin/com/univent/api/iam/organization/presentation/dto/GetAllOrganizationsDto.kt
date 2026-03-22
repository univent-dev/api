package com.univent.api.iam.organization.presentation.dto

import java.time.Instant

sealed class GetAllOrganizationsDto {
    data class Req(
        val pageSize: Int = 10,
        val cursorId: Long? = null,
        val cursorDate: Instant? = null
    ): GetAllOrganizationsDto()
}
