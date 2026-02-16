package com.univent.api.iam.user.infrastructure

import org.springframework.data.jpa.repository.JpaRepository

interface UserJpaStore: JpaRepository<UserEntity, Long>
