package com.univent.api.iam.user.application.view

import java.time.Instant

interface UserReader {
    fun findById(id: Long): UserView?
    fun findAllByCursor(pageSize: Int, cursorId: Long?, cursorDate: Instant?): List<UserAdminView>
}
