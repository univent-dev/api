package com.univent.api.iam.user.infrastructure

import com.univent.api.iam.user.application.view.UserAdminView
import com.univent.api.iam.user.application.view.UserReader
import com.univent.api.iam.user.application.view.UserView
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Repository
import java.time.Instant

@Repository
class UserReaderImpl(
    private val userJpaReader: UserJpaReader,
): UserReader {
    override fun findById(id: Long): UserView? {
        val userEntity = userJpaReader.findById(id).orElse(null) ?: return null
        return UserView(
            email = userEntity.email
        )
    }

    override fun findAllByCursor(
        pageSize: Int,
        cursorId: Long?,
        cursorDate: Instant?
    ): List<UserAdminView> {
        return userJpaReader.findAllByCursor(
            pageable = PageRequest.of(0,pageSize),
            cursorId = cursorId,
            cursorDate = cursorDate
        ).map { it.toView() }
    }
}
