package com.univent.api.common.core.infrastructure

import com.univent.api.common.core.domain.IdGenerator
import org.springframework.stereotype.Component

@Component
class SnowFlakeIdGenerator(
    private val properties: SnowFlakeProperties
) : IdGenerator {

    private val epoch = 1680000000000L
    private val datacenterIdBits = 5L
    private val workerIdBits = 5L
    private val sequenceBits = 12L

    private val maxDatacenterId = -1L xor (-1L shl datacenterIdBits.toInt())
    private val maxWorkerId = -1L xor (-1L shl workerIdBits.toInt())
    private val sequenceMask = -1L xor (-1L shl sequenceBits.toInt())

    private val workerIdShift = sequenceBits
    private val datacenterIdShift = sequenceBits + workerIdBits
    private val timestampLeftShift = sequenceBits + workerIdBits + datacenterIdBits

    private var lastTimestamp = -1L
    private var sequence = 0L

    init {
        require(properties.datacenterId in 0..maxDatacenterId) {
            "datacenterId는 0에서 $maxDatacenterId 사이여야 합니다."
        }
        require(properties.workerId in 0..maxWorkerId) {
            "workerId는 0에서 $maxWorkerId 사이여야 합니다."
        }
    }

    @Synchronized
    override fun generateId(): Long {
        var timestamp = currentTime()

        if (timestamp < lastTimestamp) {
            throw RuntimeException("시간이 역행했습니다. ID를 생성할 수 없습니다.")
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

        return ((timestamp - epoch) shl timestampLeftShift.toInt()) or
                ((properties.datacenterId and maxDatacenterId) shl datacenterIdShift.toInt()) or
                ((properties.workerId and maxWorkerId) shl workerIdShift.toInt()) or
                sequence
    }

    private fun waitNextMillis(lastTimestamp: Long): Long {
        var timestamp = currentTime()

        while (timestamp <= lastTimestamp) {
            Thread.onSpinWait()
            timestamp = currentTime()
        }
        return timestamp
    }

    private fun currentTime(): Long = System.currentTimeMillis()
}