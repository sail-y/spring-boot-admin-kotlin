/*
 * Copyright (c) 2016 xiaomaihd and/or its affiliates.All Rights Reserved.
 *            http://www.xiaomaihd.com
 */
package com.dmc.util

import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails

/**
 * Created by YangFan on 2016/11/28 下午2:23.
 *
 *
 */
object SessionUtil {

    val currUid: Long
        get() {
            val userDetails = SecurityContextHolder.getContext()
                    .authentication
                    .principal as UserDetails
            return userDetails.username.toLong()
        }

    fun clear() {

        SecurityContextHolder.clearContext()
    }
}
