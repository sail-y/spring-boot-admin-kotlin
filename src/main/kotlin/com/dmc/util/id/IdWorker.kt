/*
 * Copyright (c) 2014 xiaomaihd and/or its affiliates.All Rights Reserved.
 *            http://www.xiaomaihd.com
 */
package com.dmc.util.id

/**
 * Created by YangFan on 15/4/21 下午6:34.
 *
 *
 */
class IdWorker(private val workerId: Long) {
    private var sequence = 0L

    private var lastTimestamp = -1L

    init {
        if (workerId > maxWorkerId || workerId < 0) {
            throw IllegalArgumentException(String.format(
                    "worker Id can't be greater than %d or less than 0",
                    maxWorkerId))
        }
    }

    @Synchronized fun nextId(): Long {
        var timestamp = this.timeGen()
        if (this.lastTimestamp == timestamp) {
            //当前毫秒内，则+1
            this.sequence = this.sequence + 1 and sequenceMask
            if (this.sequence == 0L) {
                println("###########" + sequenceMask)
                //当前毫秒内计数满了，则等待下一秒
                timestamp = this.tilNextMillis(this.lastTimestamp)
            }
        } else {
            this.sequence = 0
        }
        //时间错误

        if (timestamp < this.lastTimestamp) {
            try {
                throw Exception(
                        String.format(
                                "Clock moved backwards.  Refusing to generate id for %d milliseconds",
                                this.lastTimestamp - timestamp))
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }

        this.lastTimestamp = timestamp
        //ID偏移组合生成最终的ID，并返回ID
        val nextId = timestamp - twepoch shl timestampLeftShift or (this.workerId shl workerIdShift) or this.sequence
        //  System.out.println("timestamp:" + timestamp + ",timestampLeftShift:"
        //    + timestampLeftShift + ",nextId:" + nextId + ",workerId:"
        //    + workerId + ",sequence:" + sequence);
        return nextId
    }

    //等待下一个毫秒的到来
    private fun tilNextMillis(lastTimestamp: Long): Long {
        var timestamp = this.timeGen()
        while (timestamp <= lastTimestamp) {
            timestamp = this.timeGen()
        }
        return timestamp
    }

    private fun timeGen(): Long {
        return System.currentTimeMillis()
    }

    companion object {
        private val twepoch = 1361753741828L
        //机器标识位数
        private val workerIdBits = 5
        //机器ID最大值
        val maxWorkerId = -1L xor (-1L shl workerIdBits)
        //毫秒内自增位
        private val sequenceBits = 10
        //机器ID偏左移位
        private val workerIdShift = sequenceBits
        //时间毫秒左移
        private val timestampLeftShift = sequenceBits + workerIdBits
        val sequenceMask = -1L xor (-1L shl sequenceBits)
    }


}
