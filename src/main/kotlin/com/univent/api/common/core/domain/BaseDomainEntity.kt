package com.univent.api.common.core.domain

import com.univent.api.common.core.domain.vo.Identifier
import java.io.Serializable

abstract class BaseDomainEntity<ID : Identifier<out Serializable>>(
    val id: ID
): Serializable {
    fun sameIdentityAs(other: BaseDomainEntity<*>?): Boolean {
        return other != null && this.id == other.id
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        // 같은 도메인 엔티티 계열인지, 같은 클래스인지 엄격히 체크
        if (other !is BaseDomainEntity<*> || this::class != other::class) return false

        // ID 기반 동질성 확인
        return id == other.id
    }

    override fun hashCode(): Int = id.hashCode()

    override fun toString(): String {
        return "${this::class.simpleName}(id=$id)"
    }
}
