package com.univent.api.iam.organization.infrastructure

import com.univent.api.iam.organization.application.view.OrganizationAdminView
import com.univent.api.iam.organization.application.view.OrganizationReader
import com.univent.api.iam.organization.application.view.OrganizationView
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Repository
import java.time.Instant

@Repository
class OrganizationReaderImpl(
    private val organizationJpaReader: OrganizationJpaReader
) : OrganizationReader {
    override fun findById(id: Long): OrganizationView? {
        val entity = organizationJpaReader.findById(id).orElse(null) ?: return null
        return OrganizationView(
            name = entity.name,
            contact = entity.contact
        )
    }

    override fun findAllWithCursor(
        pageSize: Int,
        cursorId: Long?,
        cursorDate: Instant?
    ): List<OrganizationAdminView> {
        return organizationJpaReader.findAllByCursor(
            pageable = PageRequest.of(0, pageSize),
            cursorId = cursorId,
            cursorDate = cursorDate
        ).map { it.toView() }
    }
}
