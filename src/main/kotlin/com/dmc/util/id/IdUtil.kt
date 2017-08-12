/*
 * Copyright (c) 2014 xiaomaihd and/or its affiliates.All Rights Reserved.
 *            http://www.xiaomaihd.com
 */
package com.dmc.util.id

/**
 * Created by YangFan on 15/4/24 下午3:12.
 *
 *
 * ID工具类
 */
object IdUtil {

    private val idWorker = IdWorker(1)

    fun generateId(): Long {
        return idWorker.nextId()
    }
}