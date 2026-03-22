package com.univent.api.iam.organization.domain

import com.univent.api.common.core.domain.AggregateRoot
import com.univent.api.common.exception.CustomException
import java.time.Instant

class Organization private constructor(
    id: OrganizationId,
    name: String,
    contact: String,
    val createdAt: Instant = Instant.now(),
    updatedAt: Instant = Instant.now()
) : AggregateRoot<OrganizationId>(id) {

    var name: String = name
        private set

    var contact: String = contact
        private set

    var updatedAt: Instant = updatedAt
        private set

    init {
        validate()
    }

    companion object {
        private val CONTACT_REGEX = Regex("^010-\\d{4}-\\d{4}$")

        fun create(id: OrganizationId, name: String, contact: String): Organization {
            return Organization(id, name, contact)
        }

        fun of(
            id: OrganizationId,
            name: String,
            contact: String,
            createdAt: Instant,
            updatedAt: Instant
        ): Organization = Organization(id, name, contact, createdAt, updatedAt)
    }

    private fun validate() {
        if (name.length !in 1..63) {
            throw CustomException(OrganizationErrorCode.ORGANIZATION_INVALID_NAME_LENGTH)
        }

        if (!CONTACT_REGEX.matches(contact)) {
            throw CustomException(OrganizationErrorCode.ORGANIZATION_INVALID_CONTACT_FORMAT)
        }
    }

    fun update(newName: String?, newContact: String?) {
        newName?.let { this.name = it }
        newContact?.let { this.contact = it }

        this.updatedAt = Instant.now()
        validate()
    }

    fun delete() {

    }
}
