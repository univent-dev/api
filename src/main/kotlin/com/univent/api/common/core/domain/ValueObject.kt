package com.univent.api.common.core.domain

abstract class ValueObject {
    abstract override fun equals(other: Any?): Boolean
    abstract override fun hashCode(): Int
}
