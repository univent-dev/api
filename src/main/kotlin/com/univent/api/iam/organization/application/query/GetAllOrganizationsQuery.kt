package com.univent.api.iam.organization.application.query

import java.time.Instant

data class GetAllOrganizationsQuery(
    val pageSize: Int = 10,
    val cursorId: Long? = null,
    val cursorDate: Instant? = null
)
