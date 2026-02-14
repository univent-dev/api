package com.univent.api.common.core.infrastructure

import com.univent.api.common.core.domain.IdGenerator
import org.springframework.stereotype.Component

@Component
class SnowFlakeIdGenerator(
    private val properties: SnowFlakeProperties
) : IdGenerator { // 도메인의 인터페이스 구현

    private val epoch = 1680000000000L
    private val datacenterIdBits = 5L
    private val workerIdBits = 5L
    private val sequenceBits = 12L

    private val workerIdShift = sequenceBits
    private val datacenterIdShift = sequenceBits + workerIdBits
    private val timestampLeftShift = sequenceBits + workerIdBits + datacenterIdBits
    private val sequenceMask = -1L xor (-1L shl sequenceBits.toInt())

    private var lastTimestamp = -1L
    private var sequence = 0L

    @Synchronized
    override fun generateId(): Long {
        var timestamp = currentTime()

        if (timestamp < lastTimestamp) {
            // 시간 역행이 작다면 잠시 대기하는 로직을 추가할 수도 있음
            throw RuntimeException("Clock moved backwards. Refusing to generate id")
        }

        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1) and sequenceMask
            if (sequence == 0L) {
                timestamp = waitNextMillis(lastTimestamp)
            }
        } else {
            sequence = 0L
        }

        lastTimestamp = timestamp

        // 비트 연산 가독성 및 속도 최적화
        return ((timestamp - epoch) shl timestampLeftShift.toInt()) or
                (properties.datacenterId shl datacenterIdShift.toInt()) or
                (properties.workerId shl workerIdShift.toInt()) or
                sequence
    }

    private fun waitNextMillis(lastTimestamp: Long): Long {
        var timestamp = currentTime()
        while (timestamp <= lastTimestamp) {
            timestamp = currentTime()
        }
        return timestamp
    }

    private fun currentTime(): Long = System.currentTimeMillis()
}