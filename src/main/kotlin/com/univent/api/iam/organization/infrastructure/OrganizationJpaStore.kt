package com.univent.api.iam.organization.infrastructure

import org.springframework.data.jpa.repository.JpaRepository

interface OrganizationJpaStore : JpaRepository<OrganizationEntity, Long>
